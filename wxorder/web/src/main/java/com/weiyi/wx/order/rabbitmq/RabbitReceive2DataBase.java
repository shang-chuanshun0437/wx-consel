package com.weiyi.wx.order.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.*;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.rabbitmq.RabbitConnectionFactory;
import com.weiyi.wx.order.common.rabbitmq.topic.TestDefault;
import com.weiyi.wx.order.request.H5AddStoreOrderRequest;
import com.weiyi.wx.order.service.api.StoreOrderService;
import com.weiyi.wx.order.websocket.WebSocketManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

@Service
public class RabbitReceive2DataBase {
    @Autowired
    private  StoreOrderService storeOrderService;

    public static Channel channel = null;
    public static Connection connection = null;
    //rabbit mq topic模式接收消息
    public void topicReceiveMsg(){
        //获取连接
        connection = RabbitConnectionFactory.getConnection();
        try {
            //从连接中获取通道
            channel = connection.createChannel();
            //(声明)创建队列
            channel.queueDeclare(Constant.WX_ADD_ORDER_QUEUE_NAME_CONSOLE,false,false,false,null);
            //绑定交换机,并指定route key (只接受route key为update、delete、insert的消息)
            channel.queueBind(Constant.WX_ADD_ORDER_QUEUE_NAME_CONSOLE,Constant.WX_ADD_ORDER_EXCHANGE_NAME,"item.add");
            //定义队列的消费者
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DataBaseConsumer(channel,storeOrderService);
            //手动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(Constant.WX_ADD_ORDER_QUEUE_NAME_CONSOLE, false, consumer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
