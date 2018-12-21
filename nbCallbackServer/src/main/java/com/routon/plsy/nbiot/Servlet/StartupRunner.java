package com.routon.plsy.nbiot.Servlet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * author : wangxiwei
 */
@Component
@Order(value=1)//代表启动时加载的顺序
public class StartupRunner implements CommandLineRunner
{
    MessageSubsribeServlet messageSubsribeServlet=new MessageSubsribeServlet();

    @Override
    public void run(String... args) throws Exception{
        messageSubsribeServlet.init(messageSubsribeServlet.getServletConfig());
    }
}
