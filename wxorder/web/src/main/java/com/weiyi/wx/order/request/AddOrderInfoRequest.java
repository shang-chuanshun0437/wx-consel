package com.weiyi.wx.order.request;

import com.weiyi.wx.order.dao.entity.OrderInfo;

public class AddOrderInfoRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    //订单编号
    private String orderId;

    //点菜详情，存放在数组中
    private OrderInfo[] orderInfos;

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

    public OrderInfo[] getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(OrderInfo[] orderInfos) {
        this.orderInfos = orderInfos;
    }
}
