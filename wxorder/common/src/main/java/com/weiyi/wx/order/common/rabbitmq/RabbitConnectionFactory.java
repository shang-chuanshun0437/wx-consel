package com.weiyi.wx.order.common.rabbitmq;

import com.rabbitmq.client.*;

public class RabbitConnectionFactory {
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
}
