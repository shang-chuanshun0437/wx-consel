package com.weiyi.wx.order.common.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.*;
import com.weiyi.wx.order.common.WebSocketManage;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

public class RabbitManage {
    private static String QUEUE_NAME = "test_queue";

    //获取rabbitmq的连接
    public static Connection getConnection(){
        Connection connection = null;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.4");
        factory.setPort(5672);
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        try {
            connection = factory.newConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    //rabbit mq 发送消息
    public static void simpleSendMsg(JSON sendMsg){
        //获取连接
        Connection connection = getConnection();
        try {
            //从连接中获取通道
            Channel channel = connection.createChannel();
            //(声明)创建队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            System.out.println("++++++++++" + sendMsg.toJSONString());
            //发送消息
            channel.basicPublish("",QUEUE_NAME,null,sendMsg.toJSONString().getBytes());

            //关闭通道和连接
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //rabbit mq 接收消息
    public static void simpleReceiveMsg(){
        //获取连接
        //测试接收数据
        //获取连接
        Connection connection = getConnection();
        try {
            //从连接中获取通道
            Channel channel = connection.createChannel();
            //(声明)创建队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //定义队列的消费者
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body);
                    //JSONObject msg = JSON.parseObject(message);
                    RabbitMQMsgEntity msg = JSON.parseObject(message, new TypeReference<RabbitMQMsgEntity>() {});
                    Map<String,Session> map = WebSocketManage.getClientMap();

                    //获取websocket中的会话
                    Session session = map.get(msg.getToUserName());

                    if (session != null){
                        session.getBasicRemote().sendText(message);
                    }else {
                        //说明断开了连接，可以将数据存入数据库
                    }

                    System.out.println("Customer Received '" + msg + "'");
                    System.out.println(Thread.currentThread().getName());
                }
            };
            //自动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(QUEUE_NAME, true, consumer);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
