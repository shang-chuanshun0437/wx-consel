package com.weiyi.wx.order.response;

import com.weiyi.wx.order.domain.Statistics;

public class QueryAllVipConsumeResponse extends BaseResponse
{
    private Statistics []statistics;

    public Statistics[] getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics[] statistics) {
        this.statistics = statistics;
    }
}
