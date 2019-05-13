package com.weiyi.wx.order.request;

public class AdminAddUserRequest extends BaseRequest{
    //待接入的商家手机号
    private Long needAddPhone;

    //待接入的商家最多可以接入的店铺数量
    private Integer shopTotal;

    public Long getNeedAddPhone() {
        return needAddPhone;
    }

    public void setNeedAddPhone(Long needAddPhone) {
        this.needAddPhone = needAddPhone;
    }

    public Integer getShopTotal() {
        return shopTotal;
    }

    public void setShopTotal(Integer shopTotal) {
        this.shopTotal = shopTotal;
    }
}
