package com.weiyi.wx.order.request;

import com.weiyi.wx.order.dao.entity.OrderInfo;

public class DeleteStoreOrderRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    private String orderId;

    //餐桌编号
    private Integer tableId;

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

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
}
