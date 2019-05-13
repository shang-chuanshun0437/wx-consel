package com.weiyi.wx.order.dao.entity;

public class PaySetting
{
    private Long userPhone;

    private String appId;

    private String mchId;

    private String openId;

    private String wxKey;

    private Integer payType;

    private String createTime;

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getWxKey() {
        return wxKey;
    }

    public void setWxKey(String wxKey) {
        this.wxKey = wxKey;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "PaySetting{" +
                "userPhone=" + userPhone +
                ", appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", openId='" + openId + '\'' +
                ", wxKey='" + wxKey + '\'' +
                ", payType=" + payType +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
