package com.weiyi.wx.order.request;

public class DeleteOrderInfoRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    private String orderId;

    private Integer foodId;

    //点餐类型
    private Integer orderType;

    //点餐类型
    private String createTime;

    private Double totalPrice;

    private Double vipTotalPrice;

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

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getVipTotalPrice() {
        return vipTotalPrice;
    }

    public void setVipTotalPrice(Double vipTotalPrice) {
        this.vipTotalPrice = vipTotalPrice;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
