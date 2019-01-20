package com.weiyi.wx.order.service.request;

import com.weiyi.wx.order.dao.entity.OrderInfo;

public class AddOrderInfoRequest {
    //商家手机号
    private Long userPhone;

    //店铺编号
    private String storeId;

    private String orderId;

    private OrderInfo[] orderInfos;

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

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
