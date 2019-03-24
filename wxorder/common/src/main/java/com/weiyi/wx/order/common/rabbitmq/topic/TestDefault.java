package com.weiyi.wx.order.common.rabbitmq.topic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class TestDefault extends DefaultConsumer{
    private Channel channel;

    public TestDefault(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException
    {
        String message = new String(body, "UTF-8");
        try {
            Thread.sleep(1000L);
        }catch (Exception e){

        }
        System.out.println("Customer Received '" + message + "'");
        //接收到消息后，进行应答
        channel.basicAck(envelope.getDeliveryTag(),false);
        System.out.println("*********TestDefault*********");
    }
}
