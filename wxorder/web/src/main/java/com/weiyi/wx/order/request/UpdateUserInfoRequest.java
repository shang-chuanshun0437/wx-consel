package com.weiyi.wx.order.request;

public class UpdateUserInfoRequest extends BaseRequest{
    private Integer payType;

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}
