package com.weiyi.wx.order.dao.request;

import com.weiyi.wx.order.dao.entity.VipVisitor;

public class GetVipVisitorListRequest extends VipVisitor
{
    private Integer currentPage;

    private String beginTime;

    private String endTime;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
