package com.weiyi.wx.order.request;

public class UpdateStoreOrderStatusRequest extends BaseRequest{
    //订单编号
    private String orderId;

    private Integer orderStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
