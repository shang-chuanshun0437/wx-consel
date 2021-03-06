package com.weiyi.wx.order.common.rabbitmq.fanout;

import com.rabbitmq.client.*;
import com.weiyi.wx.order.common.rabbitmq.RabbitConnectionFactory;

import java.io.IOException;

public class Recv2
{
    private static String QUEUE_NAME = "test_publish_exchange_queue2";
    private static String EXCHANGE_NAME = "FANOUT_EXCHANGE";
    public static void main(String[] args)
    {
        //测试接收数据
        //获取连接
        Connection connection = RabbitConnectionFactory.getConnection();
        try {
            //从连接中获取通道
            final Channel channel = connection.createChannel();
            //(声明)创建队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //绑定交换机
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
            //同一时刻服务器只向消费者发送一条消息
            channel.basicQos(1);
            //定义队列的消费者
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    try {
                        Thread.sleep(1000L);
                    }catch (Exception e){

                    }
                    System.out.println("Customer Received '" + message + "'");
                    //接收到消息后，进行应答
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };
            //手动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(QUEUE_NAME, false, consumer);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
