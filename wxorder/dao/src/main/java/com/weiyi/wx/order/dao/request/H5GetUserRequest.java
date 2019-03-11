package com.weiyi.wx.order.dao.request;

public class H5GetUserRequest
{
    private Long userPhone;

    private String storeId;

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

    @Override
    public String toString() {
        return "H5GetUserRequest{" +
                "userPhone=" + userPhone +
                ", storeId='" + storeId + '\'' +
                '}';
    }
}
