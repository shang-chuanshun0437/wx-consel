package com.weiyi.wx.order.dao.entity;

import com.weiyi.wx.order.common.utils.TimeUtil;
/*
*店铺里的餐桌
*/
public class StoreTable
{
    private int id;

    private Long userPhone;

    private String storeId;

    private Integer tableId;

    private Integer capacity;

    private String address;

    private Integer personNum ;

    private Integer status ;

    private String createTime;

    private String qrCodeUrl;

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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
}
