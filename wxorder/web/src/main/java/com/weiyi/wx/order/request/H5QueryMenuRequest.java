package com.weiyi.wx.order.request;

public class H5QueryMenuRequest{
    //商家手机号
    private Long userPhone;

    //店铺编号
    private String storeId;

    //餐桌编号：需要校验餐桌编号是否存在
    private Integer tableId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
}
