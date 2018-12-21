package com.routon.plsy.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{user}")
@Component
public class NBServerEndpoint {

    private Session session;

    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    public static ConcurrentHashMap<String, Session> getSessionMap(){
        return sessionMap;
    }

    private static Logger logger = LoggerFactory.getLogger(NBServerEndpoint.class);

    @OnOpen
    public void open(Session session, @PathParam(value="user")String user) throws IOException {
        this.session = session;
        sessionMap.put(session.getId(), session);
        logger.info("->open : " + session.getId());
        session.getBasicRemote().sendText("connection opend");
    }

    @OnMessage
    public void inMessage(String message) throws IOException {
        logger.info("->onMessage : " + this.session.getId());
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void end() {
        logger.info("->close : " + this.session.getId());
        sessionMap.remove(this.session.getId());
    }

    @OnError
    public void onError(Throwable t) {
        logger.error("->error : " + this.session.getId());
        sessionMap.remove(this.session.getId());
        //t.printStackTrace();
    }

    //用于广播数据
    public static void broadcast(String message){
        Set<String> keySet = sessionMap.keySet();
        synchronized (keySet) {
            for (String key : keySet) {
                try {
                    sessionMap.get(key).getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }
}
