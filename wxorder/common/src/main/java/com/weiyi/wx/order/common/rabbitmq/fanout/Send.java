package com.weiyi.wx.order.common.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weiyi.wx.order.common.rabbitmq.RabbitConnectionFactory;

/*
*订阅模式:Fanout exchange 当有队列绑定到该交换机上时，交换机上的消息会发送给所有的队列
*/
public class Send
{
    private static String EXCHANGE_NAME = "FANOUT_EXCHANGE";

    public static void main(String[] args)
    {
        //测试发送数据
        //获取连接
        Connection connection = RabbitConnectionFactory.getConnection();
        try {
            //从连接中获取通道
            Channel channel = connection.createChannel();
            //(声明)创建交换机
            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

            for (int i = 0; i < 20;i++){
                String sendMsg = "hello world." + i;

                //发送消息
                channel.basicPublish(EXCHANGE_NAME,"",null,sendMsg.getBytes());
            }

            //关闭通道和连接
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
