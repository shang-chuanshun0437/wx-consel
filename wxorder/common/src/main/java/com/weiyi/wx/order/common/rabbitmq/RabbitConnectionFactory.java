package com.weiyi.wx.order.common.rabbitmq;

import com.rabbitmq.client.*;
import com.weiyi.wx.order.common.constant.Constant;

public class RabbitConnectionFactory {
    //获取rabbitmq的连接
    public static Connection getConnection(){
        Connection connection = null;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.CONSOLE_SERVER_IP);
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
