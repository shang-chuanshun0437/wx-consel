package com.weiyi.wx.order.common.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weiyi.wx.order.common.rabbitmq.RabbitConnectionFactory;

/*
*订阅模式:Direct exchange(直连交换机) 当有队列绑定到该交换机上时，交换机上的消息会根据路由key发送到指定的队列
*/
public class Send
{
    private static String EXCHANGE_NAME = "TOPIC_EXCHANGE";

    public static void main(String[] args)
    {
        //测试发送数据
        //获取连接
        Connection connection = RabbitConnectionFactory.getConnection();
        try {
            //从连接中获取通道
            Channel channel = connection.createChannel();
            //(声明)创建交换机
            channel.exchangeDeclare(EXCHANGE_NAME,"topic");

            //发送消息时，指定route key
            channel.basicPublish(EXCHANGE_NAME,"item.update",null,"item.update".getBytes());
            channel.basicPublish(EXCHANGE_NAME,"item.delete",null,"item.delete".getBytes());
            channel.basicPublish(EXCHANGE_NAME,"item.insert",null,"item.insert".getBytes());

            //关闭通道和连接
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
