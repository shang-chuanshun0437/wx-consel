package com.weiyi.wx.order.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket")
public class WebSocketManage {
    private Logger logger = LoggerFactory.getLogger(WebSocketManage.class);

    //用来存储websocket session
    private static Map<String,Session> clientMap = new ConcurrentHashMap<String,Session>();

    //用来统计连接数
    private static AtomicInteger count = new AtomicInteger(0);

    //当前端连接时会首先调用该方法
    @OnOpen
    public void open(Session session){
        //获取前端传过来的参数
        String param = session.getQueryString();
        String storeId = param.split("=")[1];
        if (logger.isDebugEnabled()){
            logger.debug("==================websocket connected ,the storeId is :{}==================",storeId);
        }
        //将用户连接信息存入map
        clientMap.put(storeId,session);
        count.incrementAndGet();

        /*try {
            session.getBasicRemote().sendText("123456");
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }

    //当前端关闭连接时调用该方法
    @OnClose
    public void close(Session session){
        //获取前端传过来的参数:ws://127.0.0.1:8080/smartlock/websocket?storeId=55555
        String param = session.getQueryString();
        String storeId = param.split("=")[1];
        if (logger.isDebugEnabled()){
            logger.debug("============websocket closed ,the storeId is :{}================",storeId);
        }
        //将用户连接信息从map中删除
        clientMap.remove(storeId);
        count.decrementAndGet();
    }

    //当前端发送消息时调用该方法
    @OnMessage
    public void message(Session session,String msg){
        if (logger.isDebugEnabled()){
            logger.debug("============websocket receive msg ,the msg is :{}================",msg);
        }
    }

    @OnError
    public void onError(Throwable throwable, Session session) {
        throwable.printStackTrace();
    }

    public static Map<String, Session> getClientMap() {
        return clientMap;
    }

    public static AtomicInteger getCount() {
        return count;
    }
}
