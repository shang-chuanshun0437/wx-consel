package com.weiyi.wx.order.request;

public class QueryStoreOrderRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    private Integer tableId;

    //会员编号
    private String vipNum;

    //支付方式：1 未支付； 2 前台支付； 3 支付宝支付（通过点餐页面支付）；4 微信支付（通过点餐页面支付）
    private Integer payType;

    //订单状态：1 商家已接收该订单  2商家未接收该订单
    private Integer orderStatus;

    //订单来源：1 扫描点餐  2前台点餐 3 美团点餐  4 饿了吗点餐
    private Integer source;

    private String beginTime;

    private String endTime;

    //查询第几页
    private Integer currentPage;

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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getVipNum() {
        return vipNum;
    }

    public void setVipNum(String vipNum) {
        this.vipNum = vipNum;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
