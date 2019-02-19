package com.weiyi.wx.order.response;

import com.weiyi.wx.order.domain.StatisticsPercent;

public class QueryAllVipConsumePercentResponse extends BaseResponse
{
    private StatisticsPercent []statisticsPercents;

    public StatisticsPercent[] getStatisticsPercents() {
        return statisticsPercents;
    }

    public void setStatisticsPercents(StatisticsPercent[] statisticsPercents) {
        this.statisticsPercents = statisticsPercents;
    }
}
