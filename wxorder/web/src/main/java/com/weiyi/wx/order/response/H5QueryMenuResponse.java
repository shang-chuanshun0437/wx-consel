package com.weiyi.wx.order.response;

import com.weiyi.wx.order.domain.Goods;

public class H5QueryMenuResponse extends BaseResponse
{
    private Goods[] goods;

    //店铺名称
    private String storeName;

    //商家要求的支付方式：1 先下单，就餐完毕后，再去前台结账 2通过手机支付后，再就餐
    private int payType;

    public Goods[] getGoods() {
        return goods;
    }

    public void setGoods(Goods[] goods) {
        this.goods = goods;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
