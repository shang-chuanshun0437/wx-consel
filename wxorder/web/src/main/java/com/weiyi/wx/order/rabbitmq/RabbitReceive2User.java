package com.weiyi.wx.order.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.*;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.rabbitmq.RabbitConnectionFactory;
import com.weiyi.wx.order.controller.StoreOrderController;
import com.weiyi.wx.order.request.H5AddStoreOrderRequest;
import com.weiyi.wx.order.service.request.AddStoreOrderRequest;
import com.weiyi.wx.order.websocket.WebSocketManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

public class RabbitReceive2User {
    public static Channel channel = null;
    public static Connection connection = null;
    private Logger logger = LoggerFactory.getLogger(StoreOrderController.class);
    //rabbit mq topic模式接收消息
    public void topicReceiveMsg(){
        //获取连接
        connection = RabbitConnectionFactory.getConnection();
        try {
            //从连接中获取通道
            channel = connection.createChannel();
            //(声明)创建队列
            channel.queueDeclare(Constant.WX_ADD_ORDER_QUEUE_NAME_USER,false,false,false,null);
            //绑定交换机,并指定route key (只接受route key为update、delete、insert的消息)
            channel.queueBind(Constant.WX_ADD_ORDER_QUEUE_NAME_USER,Constant.WX_ADD_ORDER_EXCHANGE_NAME,"item.add");
            //定义队列的消费者
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body);
                    if (logger.isDebugEnabled()){
                        logger.debug("==================queue msg is :{}==================",message);
                    }
                    AddStoreOrderRequest request = JSON.parseObject(message, new TypeReference<AddStoreOrderRequest>() {});

                    //获取websocket中的会话
                    Map<String,Session> map = WebSocketManage.getClientMap();
                    Session session = map.get(request.getStoreId());

                    if (session != null){
                    //先休眠3秒，防止订单未写入数据库，导致前端查询不到订单
                        try {
                            Thread.sleep(3000);
                            session.getBasicRemote().sendText(String.valueOf(request.getTableId()));
                        }catch (Exception e){
                           e.printStackTrace();
                         }
                    }
                }
            };
            //自动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(Constant.WX_ADD_ORDER_QUEUE_NAME_USER, true, consumer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
