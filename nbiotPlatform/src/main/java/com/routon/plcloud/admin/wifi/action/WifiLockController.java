package com.routon.plcloud.admin.wifi.action;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author wangxiwei
 *
 */
@Controller
public class WifiLockController {
	
	@RequestMapping(value ="/wifi/list")
	public String list(Model model){
		
		return "wifi/wifiShow";
		
	}
	
	@RequestMapping(value ="/wifi/openListner")
	public String open(Model model){
		// Server端监听10000端口
        ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(8888);
			// 等待客户端连接，此时进入阻塞状态
	        Socket socket = serverSocket.accept();
	        System.out.println("Connected: " + socket.getRemoteSocketAddress());
	        // 从Socket读取数据
	        InputStream inputStream = socket.getInputStream();
	        byte[] b = new byte[1024];
	        int length = inputStream.read(b);
	        System.out.println(length + " Bytes Received");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "wifi/wifiShow";
		
	}
	
}
