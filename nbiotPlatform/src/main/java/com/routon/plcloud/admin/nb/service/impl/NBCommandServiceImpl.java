package com.routon.plcloud.admin.nb.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.service.signalingDelivery.PostAsynCommandV4;
import com.routon.plcloud.admin.nb.service.NBCommandService;
import com.routon.plcloud.common.model.Heartbeats;
import com.routon.plcloud.common.persistence.HeatbeatsMapper;

/**
 * 
 * @author wangxiwei
 * 指令下发接口
 */
@Service
public class NBCommandServiceImpl implements NBCommandService {
	
	@Override
	public String sendWhiteNames(String deviceId, int personId, String authority, String card1) throws Exception {
		//起始时间结束时间 目前写死获取当前时间
		Date dt = new Date();
		long lSysTime = dt.getTime() / 1000;
		long lSysTimeEnd = lSysTime + (3600 * 24 * 7);
		byte[] timeByte = long2Bytes(lSysTime);
		byte[] timeByteEnd = long2Bytes(lSysTimeEnd);
		byte[] timeBytesRes = new byte[4];
		byte[] timeBytesResEnd = new byte[4];
		System.arraycopy(timeByte, 4, timeBytesRes, 0, 4);
		System.arraycopy(timeByteEnd, 4, timeBytesResEnd, 0, 4);
		
		byte[] timeBytesResRev = reverse(timeBytesRes);
		byte[] timeBytesResEndRev = reverse(timeBytesResEnd);
		System.out.println(lSysTime);
		String s = bytesToHexFun3(timeBytesResRev);
		String s1 = bytesToHexFun3(timeBytesResEndRev);
		String e = bytesToHexFun3(timeBytesRes);
		String e1 = bytesToHexFun3(timeBytesResEnd);
		System.out.println(s);
		System.out.println(e);
		System.out.println(s1);
		System.out.println(e1);
		//基本字节
		byte[] data = new byte[4];
		data[0] = (byte) 0x1C; //数据长度
        data[1] = (byte) 0x01; //命令头
        data[2] = (byte) 0x00; //命令序号
        data[3] = (byte) 0x01;
        /*
         * 参数一：
         * 人员ID(4字节)+人员权限(1字节)+验证方式(1字节)+有效时间起始(4字节)+有效时间结束(4字节)
		         其中有效时间起始和结束为long型数据,16进制小端格式,无效值为0xFFFFFFFF
         */
        byte[] para1 = new byte[4+1+1+4+4];
        byte[] personidbyte = intToBytes(personId);
        System.arraycopy(personidbyte, 0, para1, 0, personidbyte.length);
        /*
         * 参数1中的人员权限取值:
		   0x01   100%
           0x02   50%权限
         */
        byte[] authorByte = new byte[1];
		if(authority.equals("1")){
			authorByte[0] = (byte) 0x01;
		} else if(authority.equals("2")){
			authorByte[0] = (byte) 0x02;
		}
        System.arraycopy(authorByte, 0, para1, 4, authorByte.length);
        //验证方式
        byte[] res = new byte[1];
        res[0] = (byte) 0x01;
        System.arraycopy(res , 0, para1, 5, 1);
        System.arraycopy(timeBytesResRev, 0, para1, 6, timeBytesResRev.length);
        System.arraycopy(timeBytesResEndRev, 0, para1, 10, timeBytesResEndRev.length);
        //参数二：白名单类型，白名单类型目前只有一种 0x01
        byte[] para2 = new byte[1];
        para2[0] = 0x01;
        //参数三：有效数据
        byte[] para3 = new byte[10];
        //cardid对应人员id
        byte[] card1HEX = HexString2Bytes(card1);
        System.arraycopy(card1HEX, 0, para3, 0, card1HEX.length);
        //字节数组拼接
        byte[] postByte = new byte[4+14+1+10+1];
        System.arraycopy(data, 0, postByte, 0, data.length);
        System.arraycopy(para1, 0, postByte, data.length, para1.length);
        System.arraycopy(para2, 0, postByte, data.length + para1.length, para2.length);
        System.arraycopy(para3, 0, postByte, data.length + para1.length + para2.length, para3.length);
        //校验和
        postByte[29] = (byte) 0x61;
        String cmd = bytesToHexFun3(postByte);
        System.out.println("send hex string:" + cmd); 
		String result = PostAsynCommandV4.postCommand(deviceId, cmd, 0);
		return result;
	}

	@Override
	public String delWhiteNames(String delsel, String personId, String deviceId) throws Exception {
		String result=null;
		String cmd=null;
		/*
		 * delsel为1时  删除所有白名单
		 * delsel为0时  删除单条白名单
		 */
		if(delsel.equals("1")) {
			byte[] data=new byte[6];
			data[0] = (byte) 0x04; //数据长度
			data[1] = (byte) 0x02; //命令头
			data[2] = (byte) 0x00; //命令序号
			data[3] = (byte) 0x01;
			data[4] = (byte) 0x01; //删除所有白名单
			data[5] = (byte) 0xAA; //校验和
		    cmd=bytesToHexFun3(data);
			System.out.println("send hex string:"+cmd);
			result=PostAsynCommandV4.postCommand(deviceId, cmd,0);
		}else if(delsel.equals("0")) {
			byte[] data=new byte[10];
			data[0] = (byte) 0x08; //数据长度
			data[1] = (byte) 0x02; //命令头
			data[2] = (byte) 0x00; //命令序号
			data[3] = (byte) 0x01;
			data[4] = (byte) 0x10; //删除单条白名单
			
	        byte[] personidbyte = intToBytes(Integer.parseInt(personId));
	        System.arraycopy(personidbyte, 0, data, 5, personidbyte.length);
	        data[9] = (byte) 0x00; //校验和
	        cmd=bytesToHexFun3(data);
			System.out.println("send hex string:"+cmd);
			result=PostAsynCommandV4.postCommand(deviceId, cmd, 0);
		}
		return result;
	}

	@Override
	public String openAndClose(int personId, String operaCode, String phone, String deviceId) throws Exception {
		byte[] personidbyte = intToBytes(personId);
		byte[] data = new byte[4];
		data[0] = (byte) 0x0D; //数据长度
	    data[1] = (byte) 0x03; //命令头
	    data[2] = (byte) 0x00; //命令序号
	    data[3] = (byte) 0x01;
	        /*
	         * 参数一：
	         * 人员ID(4字节)
	         */
	    byte[] para1 = new byte[4];
	    System.arraycopy(personidbyte, 0, para1, 0, personidbyte.length);
	    /*
	     * 参数二：
	     * 手机唯一标识符
	     */
	    if(phone == null){
	    	phone = "";
	    }
	    byte[] para2 = new byte[5];
    	System.arraycopy(phone.getBytes(), 0, para2, 0, phone.getBytes().length);
	    /*
	     * 参数三：
	     * 操作码
	     */
	    byte[] para3 = new byte[1];
	    /*
		 * operaCode为1时  
		 * operaCode为0时  
		 */
	    if(operaCode.equals("1")) {
	    	para3[0] = (byte) 0x01;
	    } else if(operaCode.equals("0")) {
	    	para3[0] = (byte) 0x02;
	    }
	    byte[] postbyte = new byte[15];
    	System.arraycopy(data, 0, postbyte, 0, data.length);
    	System.arraycopy(para1, 0, postbyte, data.length, para1.length);
    	System.arraycopy(para2, 0, postbyte, data.length+para1.length, para2.length);
    	System.arraycopy(para3, 0, postbyte, data.length+para1.length+para2.length, para3.length);
    	postbyte[14] = 0x00; //校验和
    	String cmd = bytesToHexFun3(postbyte);
        System.out.println("send hex string:" + cmd);
		String result = PostAsynCommandV4.postCommand(deviceId, cmd, 0);
		return result;
	}

	@Override
	public String lock(String deviceId, String lockStatus) throws Exception {
		byte[] data = new byte[6];
        //data[0] = (byte) 0xAA; //同步头
        data[0] = (byte) 0x04; //数据长度
        data[1] = (byte) 0x05; //命令头
        data[2] = (byte) 0x00; //命令序号
        data[3] = (byte) 0x01;
        if(lockStatus.equals("1")){
        	data[4] = (byte) 0x01; //参数
        } else{
        	data[4] = (byte) 0x00; //参数
        }
        
        data[5] = (byte) 0xBB; //校验和
        String cmd = bytesToHexFun3(data);
        System.out.println("send hex string:" + cmd);
		String result = PostAsynCommandV4.postCommand(deviceId, cmd, 0);
		return result;
	}

	@Override
	public String clearRecord(String deviceId) throws Exception {
		byte[] data=new byte[6];
		data[0] = (byte)0x04; //数据长度
		data[1] = (byte)0x06; //命令头
		data[2] = (byte)0x00; //命令序号
		data[3] = (byte)0x01; 
		data[4] = (byte)0x00; //参数1
		data[5] = (byte) 0x11; //校验和
		String cmd = bytesToHexFun3(data);
        System.out.println("send hex string:" + cmd);
		String result=PostAsynCommandV4.postCommand(deviceId, cmd, 0);
		return result;
	}

	@Override
	public String synTime(String deviceId) throws Exception {
		byte[] data = new byte[4];
		data[0] = (byte)0x0A; //数据长度
		data[1] = (byte)0x11; //命令头
		data[2] = (byte)0x00; //命令序号
		data[3] = (byte)0x01; 	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String temp = sdf.format(new Date());
		String[] str = temp.split(" ");
		String[] str1 = str[0].split("-");
		String[] str2 = str[1].split(":");
		String year = Integer.toHexString(Integer.parseInt(str1[0])); //将得到的年份转换为16进制的字符串
		String month = null;
		if(Integer.parseInt(str1[1])<10) {
			month = str1[1];
		} else {
			month = "0"+Integer.toHexString(Integer.parseInt(str1[1]));
		}
		
		String day = null;
		if(Integer.parseInt(str1[2])<10) {
			day = str1[2];
		} else if (Integer.parseInt(str1[2])>=10 && Integer.parseInt(str1[2])<16) {
			day = "0"+Integer.toHexString(Integer.parseInt(str1[2]));
		} else if (Integer.parseInt(str1[2]) >= 16) {
			day = Integer.toHexString(Integer.parseInt(str1[2]));
		}
		
		String hour = null;
		if(Integer.parseInt(str2[0])<10) {
			hour = str2[0];
		} else if (Integer.parseInt(str2[0])>=10 && Integer.parseInt(str2[0])<16) {
			hour = "0"+Integer.toHexString(Integer.parseInt(str2[0]));
		} else if (Integer.parseInt(str2[0]) >= 16) {
			hour = Integer.toHexString(Integer.parseInt(str2[0]));
		}
		
		String min = null;
		if(Integer.parseInt(str2[1])<10) {
			min = str2[1];
		} else if (Integer.parseInt(str2[1])>=10 && Integer.parseInt(str2[1])<16) {
			min = "0"+Integer.toHexString(Integer.parseInt(str2[1]));
		} else if (Integer.parseInt(str2[1]) >= 16) {
			min = Integer.toHexString(Integer.parseInt(str2[1]));
		}
		
		String second = null;
		if(Integer.parseInt(str2[2])<10) {
			second = str2[2];
		} else if (Integer.parseInt(str2[2])>=10 && Integer.parseInt(str2[2])<16) {
			second = "0"+Integer.toHexString(Integer.parseInt(str2[1]));
		} else if (Integer.parseInt(str2[2]) >= 16) {
			second = Integer.toHexString(Integer.parseInt(str2[2]));
		}
		
		year = "0"+year;
		String y1 = year.substring(0, 2);
		String y2 = year.substring(2);
//		System.out.println(y2);
//		System.out.println(y1);
//		System.out.println(year);
//		System.out.println(month);
//		System.out.println(day);
//		System.out.println(hour);
//		System.out.println(min);
//		System.out.println(second);
//		System.out.println(temp);
		byte[] data1 = new byte[1];
		data1[0] = (byte) 0x11; //校验和
		String cmd = bytesToHexFun3(data)+y2+y1+month+day+hour+min+second+bytesToHexFun3(data1);
        System.out.println("send hex string:" + cmd);
		String result=PostAsynCommandV4.postCommand(deviceId, cmd, 0);
		return result;
	}
	
	public static String bytesToHexFun3(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for(byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }
    
	public static byte[] long2Bytes(long num) {
		byte[] byteNum = new byte[8];
		for (int ix = 0; ix < 8; ++ix) {
			int offset = 64 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}


	// 从十六进制字符串到字节数组转换
	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}
	
	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}
	
	//将字节数组倒序
	public byte[] reverse(byte[] a) {
		byte[] b = new byte[a.length];
		int n = a.length - 1;
		for (int i = 0; i < a.length; i++) {
			b[n] = a[i];
			n--;
		}
		return b;
	}
	
	/**  
	    * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用 
	    * @param value  
	    *            要转换的int值 
	    * @return byte数组 
	    */    
	public static byte[] intToBytes( int value )   
	{   
	    byte[] src = new byte[4];  
	    src[3] =  (byte) ((value>>24) & 0xFF);  
	    src[2] =  (byte) ((value>>16) & 0xFF);  
	    src[1] =  (byte) ((value>>8) & 0xFF);    
	    src[0] =  (byte) (value & 0xFF);                  
	    return src;   
	}
	
}
