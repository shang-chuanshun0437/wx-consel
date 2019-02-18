package com.weiyi.wx.order.response;

import com.weiyi.wx.order.domain.Statistics;

public class QueryVipAddNumResponse extends BaseResponse
{
    private Statistics []statistics;

    public Statistics[] getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics[] statistics) {
        this.statistics = statistics;
    }
}
