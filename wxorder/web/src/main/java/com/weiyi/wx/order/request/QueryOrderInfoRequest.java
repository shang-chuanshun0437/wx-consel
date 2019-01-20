package com.weiyi.wx.order.request;

import com.weiyi.wx.order.dao.entity.OrderInfo;

public class QueryOrderInfoRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    private String orderId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
