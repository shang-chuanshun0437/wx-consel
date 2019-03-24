package com.weiyi.wx.order.config;

import com.weiyi.wx.order.common.redis.RedisClient;
import com.weiyi.wx.order.rabbitmq.RabbitReceive2DataBase;
import com.weiyi.wx.order.rabbitmq.RabbitReceive2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class ExitApplication {
    //在应用退出前，关闭掉rabbitmq的通道
    @PreDestroy
    public void destroy() {
        try{
            RabbitReceive2DataBase.channel.close();
            RabbitReceive2DataBase.connection.close();
            RabbitReceive2User.channel.close();
            RabbitReceive2User.connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
