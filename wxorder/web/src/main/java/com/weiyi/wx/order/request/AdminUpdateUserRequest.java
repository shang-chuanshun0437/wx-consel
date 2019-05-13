package com.weiyi.wx.order.request;

public class AdminUpdateUserRequest extends BaseRequest{
    //待删除的商家手机号
    private Long needUpdatePhone;

    private Integer shopTotal;

    public Long getNeedUpdatePhone() {
        return needUpdatePhone;
    }

    public void setNeedUpdatePhone(Long needUpdatePhone) {
        this.needUpdatePhone = needUpdatePhone;
    }

    public Integer getShopTotal() {
        return shopTotal;
    }

    public void setShopTotal(Integer shopTotal) {
        this.shopTotal = shopTotal;
    }
}
