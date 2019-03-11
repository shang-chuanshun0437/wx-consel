package com.weiyi.wx.order.request;

public class H5QueryVipVisitorListRequest{
    //商家编号
    private Long userPhone;

    //会员ID
    private String vipId;

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }
}
