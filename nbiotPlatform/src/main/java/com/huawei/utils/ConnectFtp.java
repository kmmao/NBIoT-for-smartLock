package com.huawei.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import sun.misc.BASE64Encoder;

/** 
 * Description: 向FTP服务器上传文件 
 * @param url FTP服务器hostname 
 * @param port FTP服务器端口 
 * @param username FTP登录账号 
 * @param password FTP登录密码 
 * @param path FTP服务器保存目录 
 * @param filename 上传到FTP服务器上的文件名 
 * @param input 输入流 
 * @return 成功返回true，否则返回false 
 */ 
public class ConnectFtp {
	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {  
	    boolean success = false;  
	    FTPClient ftp = new FTPClient();  
	    try {  
	        int reply;  
	        ftp.connect(url, port);//连接FTP服务器  
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
	        ftp.login(username, password);//登录  
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }  
	        ftp.changeWorkingDirectory(path);  
	        ftp.storeFile(filename, input);           
	          
	        input.close();  
	        ftp.logout();  
	        success = true;  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	    }  
	    return success;  
	}
	
	public static InputStream readfileToBase64(String ftpFile, String url, int port, String username, String password){
		FTPClient ftp = new FTPClient();
		//String result = null;
		InputStream is = null;
		try {
        int reply;  
        ftp.connect(url, port);//连接FTP服务器  
        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
        ftp.login(username, password);//登录  
        reply = ftp.getReplyCode();  
        if (!FTPReply.isPositiveCompletion(reply)) {  
            ftp.disconnect();  
        } 
		ftp.setControlEncoding("UTF-8"); // 中文支持
		//不添加FTP上传类型就会造成图片损坏
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        is = ftp.retrieveFileStream(ftpFile);
        //result = imageToBase64(is);
		}catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	    }  
        return is;
	}
	
	public static String imageToBase64(InputStream in) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	    byte[] data = null;
	    // 读取图片字节数组
	    try {
	        data = new byte[in.available()];
	        in.read(data);
	        in.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 对字节数组Base64编码
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
	
	public static void main(String[] args) throws IOException{
		//readfile("D:\\ftp134\\facephoto\\05D481A21C26701A.jpg", "172.16.42.134", 21, "134", "123456");
	}
}
