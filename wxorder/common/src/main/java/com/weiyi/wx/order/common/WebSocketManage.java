package com.weiyi.wx.order.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weiyi.wx.order.common.rabbitmq.RabbitMQMsgEntity;
import com.weiyi.wx.order.common.rabbitmq.RabbitManage;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket")
public class WebSocketManage {

    //用来存储websocket session
    private static Map<String,Session> clientMap = new ConcurrentHashMap<String,Session>();

    //用来统计连接数
    private static AtomicInteger count = new AtomicInteger(0);

    //当前端连接时会首先调用该方法
    @OnOpen
    public void open(Session session){
        //获取前端传过来的参数
        String param = session.getQueryString();
        String userName = param.split("=")[1];

        //将用户连接信息存入map
        clientMap.put(userName,session);
        count.incrementAndGet();
        System.out.println("open: " + session.getId() + "userName: " + userName);
    }

    //当前端关闭连接时调用该方法
    @OnClose
    public void close(Session session){
        //获取前端传过来的参数:ws://127.0.0.1:8080/smartlock/websocket?userName=55555
        String param = session.getQueryString();
        String userName = param.split("=")[1];

        //将用户连接信息从map中删除
        clientMap.remove(userName);
        count.decrementAndGet();
        System.out.println("close: " + session.getId() + "userName: " + userName);
    }

    //当前端发送消息时调用该方法，msg样例为：{"fromUserName":55555,"toUserName":66666,"msg":"你好"}
    @OnMessage
    public void message(Session session,String msg){
        System.out.println("msg: " + msg);
        JSONObject jsonObject = JSON.parseObject(msg);

        //存入消息队列
        RabbitManage.simpleSendMsg(jsonObject);
    }

    public static Map<String, Session> getClientMap() {
        return clientMap;
    }

    public static AtomicInteger getCount() {
        return count;
    }
}
