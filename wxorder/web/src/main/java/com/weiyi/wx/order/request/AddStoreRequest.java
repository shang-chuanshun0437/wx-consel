package com.weiyi.wx.order.request;

public class AddStoreRequest extends BaseRequest{
    private String address;

    private String storeName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
