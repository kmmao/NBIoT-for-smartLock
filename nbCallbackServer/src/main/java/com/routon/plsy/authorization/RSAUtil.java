package com.routon.plsy.authorization;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * wangxiwei
 */
public class RSAUtil {

    public static final String KEYPAIRE_PASSWORD = "plsy2017";

    public static final String KEYPAIRE_ALIAS = "plsy.keystore";

    public static final String KeyPath = "/plsy.keystore";

    public static KeyPair loadKeyPair() throws Exception{
        KeyStore keystore=KeyStore.getInstance("JKS");
        InputStream is1 = RSAUtil.class.getResourceAsStream(KeyPath);
        keystore.load(is1, KEYPAIRE_PASSWORD.toCharArray());
        KeyPair keyPair = getPrivateKey(keystore, KEYPAIRE_ALIAS, KEYPAIRE_PASSWORD.toCharArray());
        return keyPair;
    }

    public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try {
            Key key=keystore.getKey(alias,password);
            if(key instanceof PrivateKey) {
                Certificate cert=keystore.getCertificate(alias);
                PublicKey publicKey=cert.getPublicKey();
                return new KeyPair(publicKey,(PrivateKey)key);
            }
        } catch (UnrecoverableKeyException e) {

        } catch (NoSuchAlgorithmException e) {

        } catch (KeyStoreException e) {

        }
        return null;
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey
     *            私钥
     * @param cipherData
     *            密文数据
     * @return 明文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    public static void main(String[] args){
        KeyPair keyPair = null;
        try {
            keyPair = loadKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publickey = (RSAPublicKey) keyPair.getPublic();
        byte[] publicKeyByte = publickey.getEncoded();
        System.out.println(Base64.encodeBase64String(publicKeyByte));
    }
}
