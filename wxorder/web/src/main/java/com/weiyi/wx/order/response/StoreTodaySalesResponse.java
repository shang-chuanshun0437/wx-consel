package com.weiyi.wx.order.response;

import com.weiyi.wx.order.domain.Statistics;
import com.weiyi.wx.order.domain.StatisticsPercent;

public class StoreTodaySalesResponse extends BaseResponse
{
    //总销售额度
    private Statistics<Double> []allStatistics;

    //按类目统计
    private Statistics<Double> []categoryStatistics;

    private StatisticsPercent []statisticsPercents;

    public StatisticsPercent[] getStatisticsPercents() {
        return statisticsPercents;
    }

    public void setStatisticsPercents(StatisticsPercent[] statisticsPercents) {
        this.statisticsPercents = statisticsPercents;
    }

    public Statistics<Double>[] getAllStatistics() {
        return allStatistics;
    }

    public void setAllStatistics(Statistics<Double>[] allStatistics) {
        this.allStatistics = allStatistics;
    }

    public Statistics<Double>[] getCategoryStatistics() {
        return categoryStatistics;
    }

    public void setCategoryStatistics(Statistics<Double>[] categoryStatistics) {
        this.categoryStatistics = categoryStatistics;
    }
}
