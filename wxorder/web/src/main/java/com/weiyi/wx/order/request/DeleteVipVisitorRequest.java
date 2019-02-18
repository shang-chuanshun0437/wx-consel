package com.weiyi.wx.order.request;

public class DeleteVipVisitorRequest extends BaseRequest{
    //会员编号
    private String vipId;

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }
}
