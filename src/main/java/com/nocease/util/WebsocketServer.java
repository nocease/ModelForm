package com.nocease.util;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/1/2 9:14
 * @Description 指定用户id，重复登录会使之前的登录掉线
 */
@ServerEndpoint("/webSocket/{param}")
@Component
public class WebsocketServer {
    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("param") String param) {
        System.out.println("客户端" + param + "连接了");
        //将新用户存入在线的组
        clients.put(param, session);
    }

    /**
     * 客户端关闭
     *
     * @param session session
     */
    @OnClose
    public void onClose(Session session, @PathParam("param") String param) {
        System.out.println("客户端" + param + "断开了");
        //将掉线的用户移除在线的组里
        clients.remove(param);
    }

    /**
     * 发生错误
     *
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     *
     * @param message 消息对象
     */
    @OnMessage
    public void onMessage(String message, @PathParam("param") String param) {
        System.out.println("客户端" + param + "发来消息:" + message);
    }

    /**
     * 群发消息
     *
     * @param message 消息内容
     */
    public void sendToAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getAsyncRemote().sendText(message);
        }
        System.out.println("向所有客户端发送了：" + message);
    }

    /**
     * 单发消息
     *
     * @param message 消息内容
     */
    public void sendToOne(String message, String param) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            if (param.equals(sessionEntry.getKey())) {
                sessionEntry.getValue().getAsyncRemote().sendText(message);
            }
        }
        System.out.println("向客户端" + param + "发送了：" + message);
    }

}
