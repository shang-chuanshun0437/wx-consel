package com.weiyi.wx.order.request;

public class AdminDeleteUserRequest extends BaseRequest{
    //待删除的商家手机号
    private Long needDeletePhone;

    public Long getNeedDeletePhone() {
        return needDeletePhone;
    }

    public void setNeedDeletePhone(Long needDeletePhone) {
        this.needDeletePhone = needDeletePhone;
    }
}
