package com.weiyi.wx.order.dao.request;

public class H5GetMenuRequest
{
    private Long userPhone;

    private String storeId;

    private Integer tableId;

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

    @Override
    public String toString() {
        return "H5GetMenuRequest{" +
                "userPhone=" + userPhone +
                ", storeId='" + storeId + '\'' +
                ", tableId=" + tableId +
                '}';
    }
}
