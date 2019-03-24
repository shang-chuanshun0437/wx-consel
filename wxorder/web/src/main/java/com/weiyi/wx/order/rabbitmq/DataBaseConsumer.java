package com.weiyi.wx.order.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.weiyi.wx.order.controller.StoreOrderController;
import com.weiyi.wx.order.service.api.StoreOrderService;
import com.weiyi.wx.order.service.request.AddStoreOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DataBaseConsumer extends DefaultConsumer{
    private Logger logger = LoggerFactory.getLogger(DataBaseConsumer.class);
    private Channel channel = null;

    private StoreOrderService storeOrderService = null;

    public DataBaseConsumer(Channel channel,StoreOrderService storeOrderService) {
        super(channel);
        this.channel = channel;
        this.storeOrderService = storeOrderService;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException
    {
        if (logger.isDebugEnabled()){
            logger.debug("order resource is wxpay app,write order to data base.");
        }
        String message = new String(body);

        AddStoreOrderRequest request = JSON.parseObject(message, new TypeReference<AddStoreOrderRequest>() {});
        storeOrderService.createOrder(request);
        System.out.println("Customer Received storeID'" + request.getStoreId() + "'");
        System.out.println(Thread.currentThread().getName());
        //接收到消息后，进行应答
        channel.basicAck(envelope.getDeliveryTag(),false);
        System.out.println("*********TestDefault*********");
    }
}
