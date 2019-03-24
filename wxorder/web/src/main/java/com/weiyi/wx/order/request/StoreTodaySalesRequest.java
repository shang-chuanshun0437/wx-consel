package com.weiyi.wx.order.request;

public class StoreTodaySalesRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
