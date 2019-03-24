package com.weiyi.wx.order.common.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.*;
import com.weiyi.wx.order.common.constant.Constant;

public class RabbitSendManage {
    //rabbit mq topic模式发送消息
    public static void topicSendMsg(JSON sendMsg){
        //获取连接
        Connection connection = RabbitConnectionFactory.getConnection();
        try {
            //从连接中获取通道
            Channel channel = connection.createChannel();
            //(声明)创建交换机
            channel.exchangeDeclare(Constant.WX_ADD_ORDER_EXCHANGE_NAME,"topic");

            //发送消息时，指定route key
            channel.basicPublish(Constant.WX_ADD_ORDER_EXCHANGE_NAME,"item.add",null,sendMsg.toJSONString().getBytes());

            //关闭通道和连接
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
