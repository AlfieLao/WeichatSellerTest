package com.imooc.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ServerEndpoint("/WebSocketTest")
@Slf4j
public class WebSocketTest {
    private Session session;
    private  static CopyOnWriteArrayList<WebSocketTest> webSocketTests = new CopyOnWriteArrayList<>();

    @OnOpen
    public  void onOpen(Session session){
        this.session = session;
        webSocketTests.add(this);
        log.info("【webSocket消息】,有新连接,总数：{}",webSocketTests.size());
    }

    @OnClose
    public void onClose(){
        webSocketTests.remove(this);
        log.info("【webSocket消息】,断开连接,总数：{}",webSocketTests.size());
    }

    @OnMessage
    public void onMessage(String msg){
        log.info("【webSocket消息】,收到客户端的消息：{}",msg);
    }


    public void sendMessage(String msg){
        for (WebSocketTest webSocketTest:webSocketTests){
            log.info("【webSocket消息】,广播消息：{}",msg);
            try {
                webSocketTest.session.getBasicRemote().sendText(msg);
            }catch (Exception e){
                e.printStackTrace();
                log.info("【webSocket消息】,广播消息失败：{}",msg+e.getMessage());
            }
        }
    }
}
