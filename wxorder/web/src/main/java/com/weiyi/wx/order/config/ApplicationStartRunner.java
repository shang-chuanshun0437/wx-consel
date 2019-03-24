package com.weiyi.wx.order.config;

import com.weiyi.wx.order.rabbitmq.RabbitReceive2DataBase;
import com.weiyi.wx.order.rabbitmq.RabbitReceive2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartRunner implements ApplicationRunner {
    @Autowired
    private RabbitReceive2DataBase rabbitReceive2DataBase;

    private RabbitReceive2User rabbitReceive2User = new RabbitReceive2User();
    //配置程序启动时，需要启动的方法
    public void run(ApplicationArguments args) throws Exception {
        rabbitReceive2User.topicReceiveMsg();
        rabbitReceive2DataBase.topicReceiveMsg();
        System.out.println("================ApplicationStartRunner  RabbitReceive2User.topicReceiveMsg() ==============================");
    }
}
