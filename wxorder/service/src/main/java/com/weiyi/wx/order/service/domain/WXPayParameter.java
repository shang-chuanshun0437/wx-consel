package com.weiyi.wx.order.service.domain;

public class WXPayParameter {
    //公众号名称，由商户传入
    private String appId;

    //时间戳，自1970年以来的秒数
    private String timeStamp;

    //随机串
    private String nonceStr;

    //统一下单接口返回的prepay_id参数值
    private String prepayId;

    //微信签名
    private String paySign;

    //微信签名类型
    private String signType;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
