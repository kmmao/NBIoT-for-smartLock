package com.routon.plcloud.admin.order.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * 内部接口调用封装成工具类
 * 
 * @author huanggang
 *
 */
public class userloginUtils {

	private static Logger logger = LoggerFactory.getLogger(userloginUtils.class);
	// 客户端私钥模
	private static final String priKeyModulus = "a312c330af12b7ae5207dfead13b4a1274e105c78452916f2246eb6094abf8ab491953a8f56ac476049caf8a8fc224a358b25e83c1b2fbf16d21fd3bcab8f5df7faf2c42f82b50df54c52194842dbc664f073b71064f25cb3fb0ec5202ab22fc0c49469523302e54e763b66ffb2f9ab6452aa5207e51d71268a7e61f313f4859";
	// 客户端私钥指数
	private static final String prikeyExpInHex = "3982df75e6f899f10f9b195c4e2833ca689007a2c2c6ffe58ad82a20e8adf1c82ec290dfb095d3edaf58b70c0e01313cae865190f41a348283b38e04ad4d27e0f79bd2bc50513665f20fe73231836207683c004f589f81e1bb6f48ded4b1278331f2ae4c978d685d6246cedf77f968732bc7c7a4ce17a5c8fc7ec3271fef9dc5";
	// 服务端公钥模
	private static final String pubKeyModulusInHex = "9ffd9ef49967fd5a0b0a4c834e2640fdadf637c6d78b7a5f58cb8cbcd58f6575dc3f9d4b79fe9d1db2074b7080db77a39974421408c9564914fd360a64e6a4315c2f6c411b929e3cfe7e6fb948d496e200af94acf0b44cac7bd335918edf86a178ff692fb10ca9b238bb1fb1bcede1adac698f2ff6859e39cf8acc24567e82f9";
	// 服务端公钥指数
	private static final String pubKeyExpInHex = "00010001";
	// mac_key用于计算mac
	public static final String mac_key = "fcd3631882054598bf0fe9bcfb124eb7";
	// 内部接口访问地址
//	private static final String targetURL = "http://172.16.42.38:8920/";

	/**
	 * RSA算法获取公钥
	 * 
	 * @param modulusIn16Radix
	 * @param exponentIn16Radix
	 * @throws Exception
	 */
	private static RSAPublicKey CreatePublicKeyFromModulus(String modulusIn16Radix, String exponentIn16Radix)
			throws Exception {
		BigInteger m = new BigInteger(modulusIn16Radix, 16);
		BigInteger e = new BigInteger(exponentIn16Radix, 16);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
		RSAPublicKey rsaPubKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		return rsaPubKey;
	}

	/**
	 * RSA算法获取私钥
	 * 
	 * @param modulusIn16Radix
	 * @param exponentIn16Radix
	 * @throws Exception
	 */
	private static RSAPrivateKey CreatePrivateKeyFromModulus(String modulusIn16Radix, String exponentIn16Radix)
			throws Exception {
		BigInteger m = new BigInteger(modulusIn16Radix, 16);
		BigInteger e = new BigInteger(exponentIn16Radix, 16);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
		RSAPrivateKey rsaPriKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		return rsaPriKey;
	}

	/**
	 * 根据模和指数获取公钥
	 * 
	 * @param modulus
	 * @param exp
	 * @return
	 */
	private static PublicKey getPublicKey(String modulus, String exp) {
		PublicKey publickey = null;
		try {
			publickey = CreatePublicKeyFromModulus(modulus, exp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return publickey;
	}

	/**
	 * 根据模和指数获取私钥
	 * 
	 * @param modulus
	 * @param exp
	 * @return
	 */
	private static PrivateKey getPrivateKey(String modulus, String exp) {
		PrivateKey prikey = null;
		try {
			prikey = CreatePrivateKeyFromModulus(modulus, exp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prikey;
	}

	/**
	 * RSA加密算法
	 * 
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] content, RSAKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, (Key) key);
		return cipher.doFinal(content);
	}

	/**
	 * RSA解密算法
	 * 
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] content, RSAKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, (Key) key);
		return cipher.doFinal(content);
	}

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 * @throws Exception
	 */
	private static String sign(byte[] data, PrivateKey privateKey) throws Exception {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateKey);
		signature.update(data);
		return byteArr2str(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * @throws Exception
	 */
	private static boolean verify(byte[] data, PublicKey publicKey, String sign) throws Exception {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(publicKey);
		signature.update(data);
		return signature.verify(str2byteArr(sign));
	}

	/**
	 * 3DES兼容C16字节密钥加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] des3EncodeECB(byte[] key, byte[] data) throws Exception {
		Key desKey;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
		desKey = keyFactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede/ECB/NoPadding");

		cipher.init(Cipher.ENCRYPT_MODE, desKey);

		return cipher.doFinal(data);
	}

	/**
	 * 3DES兼容C16字节密钥解密
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] des3DecodeECB(byte[] key, byte[] data) throws Exception {
		Key desKey;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
		desKey = keyFactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede/ECB/NoPadding");

		cipher.init(Cipher.DECRYPT_MODE, desKey);

		return cipher.doFinal(data);
	}

	/**
	 * 计算时间戳
	 * 
	 * @return
	 */
	public static byte[] GetLocalTime() {
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = dateFormat.format(date);

		byte[] timeByte = new byte[16];
		System.arraycopy(time.getBytes(), 0, timeByte, 0, time.getBytes().length);
		return timeByte;
	}

	/**
	 * 计算mac值
	 * 
	 * @param msgdata
	 * @param len
	 * @param timeMac
	 * @return
	 */
	public static byte[] CalcMac(byte[] msgdata, int len, byte[] timeMac) {
		byte[] timeORbyte = new byte[16];

		for (int i = 0; i < len; i += 16) {
			byte[] temp = new byte[16];

			if (i + 16 < len) {
				System.arraycopy(msgdata, i, temp, 0, 16);
			} else {
				System.arraycopy(msgdata, i, temp, 0, len - i);
			}

			// 异或运算
			for (int j = 0; j < 16; j++) {
				timeORbyte[j] ^= temp[j];
			}
		}

		if (timeMac == null) {
			return timeORbyte;
		} else {
			byte[] mac = byteMerger(timeMac, timeORbyte);
			return mac;
		}
	}

	/**
	 * java byte_1和byte_2拼接成一个byte
	 * 
	 * @param byte_1
	 * @param byte_2
	 * @return
	 */
	private static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] bytearray = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, bytearray, 0, byte_1.length);
		System.arraycopy(byte_2, 0, bytearray, byte_1.length, byte_2.length);
		return bytearray;
	}

	/**
	 * 输入16字节长度密钥，得到24字节长度密钥
	 * 
	 * @param oldkey
	 * @return
	 */
	public static byte[] get24Byte(byte[] oldkey) {
		byte[] newkey;
		if (oldkey.length == 16) {
			newkey = new byte[24];
			System.arraycopy(oldkey, 0, newkey, 0, 16);
			System.arraycopy(oldkey, 0, newkey, 16, 8);
		} else {
			newkey = oldkey;
		}

		return newkey;
	}

	/**
	 * 16进制string转byte
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] str2byteArr(String str) {
		byte[] bArr = new byte[str.length() >> 1];
		for (int i = 0; i < str.length();) {
			int bArrIndex = i / 2;
			int h = char2int(str.charAt(i++));
			int l = char2int(str.charAt(i++));
			bArr[bArrIndex] = (byte) (h * 0x10 + l);
		}
		return bArr;
	}

	private static int char2int(char ch) {
		int value = -1;
		if ((ch >= '0' && ch <= '9')) {
			value = ch - '0';
		} else if (ch >= 'a' && ch <= 'f') {
			value = ch - 'a' + 10;
		} else if (ch >= 'A' && ch <= 'F') {
			value = ch - 'A' + 10;
		}
		return value;
	}

	/**
	 * byte转16进制字符串，不足2位左补零
	 * 
	 * @param bArr
	 * @return
	 */
	public static String byteArr2str(byte[] bArr) {
		String tmp = "";
		for (byte b : bArr) {
			tmp += String.format("%02x", b);
		}
		return tmp;
	}

	/**
	 * byte反转
	 * 
	 * @param array
	 * @return
	 */
	private static String reverseByArray(byte[] array) {

		byte temp;

		for (int i = 0; i < array.length / 2; i++) {
			temp = array[i];
			array[i] = array[array.length - (i + 1)];
			array[array.length - (i + 1)] = temp;
		}

		return byteArr2str(array).toLowerCase();
	}

	/**
	 * 将int转为byte数组
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		// 由高位到低位
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	/**
	 * 若字节数组以0开头，则去掉0元素后反转数组
	 * 
	 * @param byt
	 * @return
	 */
	public static byte[] deltZero(byte[] byt) {
		int zero = 0; // 统计0的个数
		int j = 0;
		byte[] newArr = null;
		if (byt[0] == 0) {
			for (int i = 0; i < byt.length; i++) {
				if (byt[i] == 0) {
					zero++;
					newArr = new byte[byt.length - zero];
					continue;
				}
				newArr[j] = byt[i];
				j++;
			}
			reverseByArray(newArr);
			return newArr;
		} else {
			reverseByArray(byt);
			return byt;
		}
	}

	private Properties getIp(){
		Properties pro = new Properties();
		InputStream in = getClass().getResourceAsStream("/jdbc.properties");
		try {
			pro.load(in);
		} catch (IOException e) {
			System.err.println("读取配置文件失败");
		}
		return pro;
	}
	
	/**
	 * 调用内部接口
	 * 
	 * @param parmJson
	 * @return
	 */
	public static String initPrivateInterface(String parmJson) {
		URL targetUrl = null;
		HttpURLConnection httpURLConnection = null;
		OutputStream outputStream = null;
		String output = null;
		InputStream is = null;
		InputStreamReader isr = null;
		try {
			userloginUtils ul =new userloginUtils();
			Properties pro = ul.getIp();
			String ip = pro.getProperty("serverip").trim();
//			System.out.println("ip地址为："+ip);
			String targetURL = "http://" + ip + ":8920/" ;
//			String targetURL = "http://" + "172.16.42.134" + ":8920/" ;
			
			targetUrl = new URL(targetURL);
			httpURLConnection = (HttpURLConnection) targetUrl.openConnection();

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");

			httpURLConnection.setDoInput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(100000);
			httpURLConnection.setReadTimeout(100000);

			outputStream = httpURLConnection.getOutputStream();
			outputStream.write(parmJson.getBytes());
			outputStream.flush();

			is = httpURLConnection.getInputStream();
			isr = new InputStreamReader(is);
			BufferedReader responseBuffer = new BufferedReader(isr);

			int code = httpURLConnection.getResponseCode();
			if (code != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + httpURLConnection.getResponseCode());
			}

			output = responseBuffer.readLine();
		} catch (MalformedURLException e) {
			System.err.println("接口URL地址无效");
			logger.error("接口URL地址无效");
		} catch (IOException e) {
			System.err.println("httpURLConnection出错");
			logger.error("httpURLConnection出错,无法连接授权服务");
		} finally {
			try {
				if (isr != null) {
					isr.close();
				}
				if(is != null){
					is.close();
				}
				if(outputStream != null){
					outputStream.close();
				}
				if(httpURLConnection !=null){
					httpURLConnection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	/**
	 * 封装参数后调用UserLogin接口
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private static String testResult(String username, String password) {
		// 客户端私钥对用户名进行签名处理
		// 获取客户端私钥
		PrivateKey prikey = getPrivateKey(priKeyModulus, prikeyExpInHex);
		String signReverse = null;
		try {
			String sign = sign(username.getBytes(), prikey);
			byte[] signbyte = str2byteArr(sign);
			// 反转签名，作为签名参数调用
			signReverse = reverseByArray(signbyte);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 服务端公钥对密码加密,得到服务端公钥
		PublicKey publicKey = getPublicKey(pubKeyModulusInHex, pubKeyExpInHex);
		String passwordmi = null;
		byte[] passw = password.getBytes();
		byte[] passwordMi1;
		try {
			passwordMi1 = encrypt(passw, (RSAKey) publicKey);
			// 反转加密后的密码作为参数
			passwordmi = reverseByArray(passwordMi1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 拼接需要加密的mac
		// 获取时间戳
		byte[] time = GetLocalTime();
		byte[] macdata = new byte[80];
		System.arraycopy(username.getBytes(), 0, macdata, 0, username.getBytes().length);
		System.arraycopy(password.getBytes(), 0, macdata, 32, password.getBytes().length);
		System.arraycopy(time, 0, macdata, 64, time.length);

		// 计算mac
		byte[] mac = CalcMac(macdata, macdata.length, time);
		// 增长mackey
		byte[] key = get24Byte(str2byteArr(mac_key));
		// 加密mac
		String hexmacMi = null;
		try {
			hexmacMi = byteArr2str(des3EncodeECB(key, mac));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 封装访问UserLogin接口所需的参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "UserLogin");
		jsonObject.put("version", 256);
		jsonObject.put("user_name", username);
		jsonObject.put("user_signal", signReverse);
		jsonObject.put("user_password", passwordmi);
		jsonObject.put("mac", hexmacMi);
		String input = jsonObject.toString();
		// 获取返回值
		String output = initPrivateInterface(input);

		return booResult(output);
	}

	/**
	 * 判断调用接口是否正确
	 * 
	 * @param output
	 * @return
	 */
	public static String booResult(String output) {
		JSONObject jsStr = JSONObject.fromObject(output);
		int result = jsStr.getInt("result");

		if (result == (0)) {
			return output;
		} else if (result == (-1)) {
			System.err.println("-1 MAC不正确");
			return null;
		} else if (result == (-2)) {
			System.err.println("-2 用户不存在");
			return null;
		} else if (result == (-3)) {
			System.err.println("-3 用户未登录");
			return null;
		} else if (result == (-4)) {
			System.err.println("-4 密码不正确");
			return null;
		} else if (result == (-5)) {
			System.err.println("-5 无权限");
			return null;
		} else if (result == (-6)) {
			System.err.println("-6 用户已存在");
			return null;
		} else if (result == (-7)) {
			System.err.println("-7 非法操作");
			return null;
		} else if (result == (-8)) {
			System.err.println("-8 超过允许上限");
			return null;
		} else if (result == (-9)) {
			System.err.println("-9 SDK不存在");
			return null;
		} else if (result == (-10)) {
			System.err.println("-10 验证客户端签名出错");
			return null;
		} else if (result == (-11)) {
			System.err.println("-11 解密用户密码出错");
			return null;
		} else if (result == (-12)) {
			System.err.println("-12 获取会话密钥出错");
			return null;
		} else if (result == (-13)) {
			System.err.println("-13 加密会话密钥出错");
			return null;
		} else {
			System.err.println("调用内部接口出错");
			return null;
		}
	}

	/**
	 * 验证服务端返回值--》验签、检查mac是否正确、得到会话密钥
	 * 
	 * @param output
	 * @return
	 */
	private static byte[] verifyResult(String output) {
		String user_name = null;
		String server_signal = null;
		String user_key = null;
		String mac1 = null;

		if (output != null) {
			JSONObject jsStr = JSONObject.fromObject(output);
			user_name = jsStr.getString("user_name");
			server_signal = jsStr.getString("server_signal");
			user_key = jsStr.getString("user_key");
			mac1 = jsStr.getString("mac");
		}

		// 服务端公钥验签 server_signal
		// 得到服务端公钥
		PublicKey publicKey = getPublicKey(pubKeyModulusInHex, pubKeyExpInHex);
		String reverse_signal = reverseByArray(str2byteArr(server_signal));
		try {
			boolean signVerify = verify(user_name.getBytes(), publicKey, reverse_signal);
			if (signVerify) {
				System.out.println("签名验证成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 反转user_key后解密得到会话密钥
		String reverse_key = reverseByArray(str2byteArr(user_key));
		PrivateKey prikey = getPrivateKey(priKeyModulus, prikeyExpInHex);
		byte[] userkeyDec = null;
		try {
			userkeyDec = decrypt(str2byteArr(reverse_key), (RSAKey) prikey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 解密mac1
		byte[] key = get24Byte(str2byteArr(mac_key));
		byte[] macJie = null;
		try {
			macJie = des3DecodeECB(key, str2byteArr(mac1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 拼凑msg_data
		byte[] msg_data5 = new byte[64];
		byte[] username = user_name.getBytes();
		int len = username.length;
		System.arraycopy(username, 0, msg_data5, 0, len);
		System.arraycopy(userkeyDec, 0, msg_data5, 32, 16);
		System.arraycopy(macJie, 0, msg_data5, 48, 16);
		String msg_dataStr = byteArr2str(msg_data5).toLowerCase();
		byte[] msg_data = str2byteArr(msg_dataStr);

		byte[] macdata = CalcMac(msg_data, msg_data.length, null);
		String macdataStr = byteArr2str(macdata);

		// 比较mac值是否正确
		String macplain = byteArr2str(macJie).substring(32);

		if (macplain.equals(macdataStr)) {
			System.out.println("mac is ok!");
			return userkeyDec;
		} else {
			System.err.println("mac error ！");
			return null;
		}
	}

	/**
	 * 输入用户名和密码，调用UserLogin接口，得到会话密钥
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static byte[] getUserkey(String username, String password) {
		// 调用UserLogin接口得到返回值
		String result = testResult(username, password);

		byte[] user_key = null;
		if (result != null) {
			// 验证返回值，验证通过得到会话密钥
			user_key = verifyResult(result);
		}
		return user_key;

	}
}
