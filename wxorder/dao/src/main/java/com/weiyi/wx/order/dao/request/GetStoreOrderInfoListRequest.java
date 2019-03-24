package com.weiyi.wx.order.dao.request;

import com.weiyi.wx.order.dao.entity.OrderInfo;

public class GetStoreOrderInfoListRequest extends OrderInfo
{
    private String beginTime;

    private String endTime;

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
