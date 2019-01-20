package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.OrderInfo;

public class QueryOrderInfoResponse extends BaseResponse
{
    private int total;

    private OrderInfo[] orderInfos;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public OrderInfo[] getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(OrderInfo[] orderInfos) {
        this.orderInfos = orderInfos;
    }
}
