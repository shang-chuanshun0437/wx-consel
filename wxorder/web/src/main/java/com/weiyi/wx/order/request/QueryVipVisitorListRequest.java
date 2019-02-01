package com.weiyi.wx.order.request;

public class AddVipVisitorRequest extends BaseRequest{
    //会员编号
    private String vipId;

    //实际充值金额
    private Double realAmount;

    //可消费的总金额
    private Double totalAmount;

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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
