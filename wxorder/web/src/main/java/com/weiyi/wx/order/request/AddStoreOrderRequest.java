package com.weiyi.wx.order.request;

import com.weiyi.wx.order.dao.entity.OrderInfo;

public class AddStoreOrderRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    private Integer tableId;

    private Integer personNum;
    //实付金额
    private Double realAmount;

    private Integer status;

    private Integer payType;

    private Integer source;

    private String vipNum;

    //点菜详情，存放在数组中
    private OrderInfo[] orderInfos;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getVipNum() {
        return vipNum;
    }

    public void setVipNum(String vipNum) {
        this.vipNum = vipNum;
    }

    public OrderInfo[] getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(OrderInfo[] orderInfos) {
        this.orderInfos = orderInfos;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }
}