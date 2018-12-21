package com.routon.plsy.nbiot.Servlet;

import com.routon.plsy.nbiot.TestSubscribeAllNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;

/**
 * author : wangxiwei
 */
public class MessageSubsribeServlet extends HttpServlet{

	private static Logger logger = LoggerFactory.getLogger(MessageSubsribeServlet.class);

	public void init(){
		logger.info("开始订阅消息......");
        TestSubscribeAllNotification nb = new TestSubscribeAllNotification();
        try {
			nb.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
