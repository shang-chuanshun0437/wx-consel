package com.weiyi.wx.order.response;

import com.weiyi.wx.order.domain.Goods;

public class H5QueryMenuResponse extends BaseResponse
{
    private Goods[] goods;

    //商家wxAppId
    private String  wxAppId;

    //店铺名称
    private String storeName;

    public Goods[] getGoods() {
        return goods;
    }

    public void setGoods(Goods[] goods) {
        this.goods = goods;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
