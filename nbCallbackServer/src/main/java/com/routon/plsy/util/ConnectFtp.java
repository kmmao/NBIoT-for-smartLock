package com.routon.plsy.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

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
	        Boolean result = ftp.changeWorkingDirectory(path);
	        System.out.println(result);
			ftp.setControlEncoding("UTF-8"); // 中文支持
			//不添加FTP上传类型就会造成图片损坏
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
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
	
	public static String readfileToBase64(String ftpFile, String url, int port, String username, String password) throws IOException{
		FTPClient ftp = new FTPClient();
        int reply;  
        ftp.connect(url, port);//连接FTP服务器  
        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
        ftp.login(username, password);//登录  
        reply = ftp.getReplyCode();  
        if (!FTPReply.isPositiveCompletion(reply)) {  
            ftp.disconnect();  
        } 
        InputStream is = ftp.retrieveFileStream(ftpFile);
        String result = Input.imageToBase64(is);
        return result;
	}
}
