package com.weiyi.wx.order.request;

public class AddVipVisitorRequest extends BaseRequest{
    //会员编号
    private String vipId;

    //实际充值金额
    private Double realAmount;

    //有效期
    private String validTime;

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }
}
