package com.weiyi.wx.order.request;

public class QueryStoreTableRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    //餐桌编号
    private Integer tableId;

    //餐桌位置
    private String address;

    //容纳人数
    private Integer capacity;

    //餐桌状态
    private Integer status;

    //查询页数
    private Integer currentPage;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
