package com.weiyi.wx.order.common.rabbitmq;

public class RabbitMQMsgEntity {
    private String fromUserName;

    private String toUserName;

    private String msg;

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RabbitMQMsgEntity{" +
                "fromUserName='" + fromUserName + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
