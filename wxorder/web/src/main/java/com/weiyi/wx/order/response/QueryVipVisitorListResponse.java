package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.VipVisitor;

public class QueryVipVisitorListResponse extends BaseResponse
{
    private int total;

    private VipVisitor[] vipVisitors;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public VipVisitor[] getVipVisitors() {
        return vipVisitors;
    }

    public void setVipVisitors(VipVisitor[] vipVisitors) {
        this.vipVisitors = vipVisitors;
    }
}
