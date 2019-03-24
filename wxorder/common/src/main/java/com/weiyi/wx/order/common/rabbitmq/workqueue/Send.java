package com.weiyi.wx.order.common.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weiyi.wx.order.common.rabbitmq.RabbitConnectionFactory;

/*
*工作队列
*/
public class Send
{
    private static String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args)
    {
        //测试发送数据
        //获取连接
        Connection connection = RabbitConnectionFactory.getConnection();
        try {
            //从连接中获取通道
            Channel channel = connection.createChannel();
            //(声明)创建队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            for (int i = 0; i < 20;i++){
                String sendMsg = "hello world." + i;

                //发送消息
                channel.basicPublish("",QUEUE_NAME,null,sendMsg.getBytes());
            }

            //关闭通道和连接
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
