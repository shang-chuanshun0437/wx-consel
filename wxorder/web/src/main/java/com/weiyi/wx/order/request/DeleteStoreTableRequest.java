package com.weiyi.wx.order.request;

public class DeleteStoreTableRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    //餐桌编号
    private Integer tableId;

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
}
