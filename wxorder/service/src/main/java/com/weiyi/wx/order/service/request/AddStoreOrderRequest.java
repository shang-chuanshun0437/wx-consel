package com.weiyi.wx.order.service.request;

import com.weiyi.wx.order.dao.entity.OrderInfo;

public class AddStoreOrderRequest{
    //商家手机号
    private Long userPhone;

    //店铺编号
    private String storeId;

    //店铺名称
    private String storeName;

    private Integer tableId;

    private Integer personNum;

    //实付金额
    private Double totalAmount;

    private Integer payType;

    private Integer source;

    private Integer orderStatus;

    private Integer orderTemp;

    private String vipNum;

    //点菜详情，存放在数组中
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

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderTemp() {
        return orderTemp;
    }

    public void setOrderTemp(Integer orderTemp) {
        this.orderTemp = orderTemp;
    }
}
