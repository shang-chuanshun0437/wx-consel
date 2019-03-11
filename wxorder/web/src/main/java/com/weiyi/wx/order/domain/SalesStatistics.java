package com.weiyi.wx.order.domain;

public class SalesStatistics{
    private String storeName;

    private Statistics<Double> []statistics;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Statistics<Double>[] getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics<Double>[] statistics) {
        this.statistics = statistics;
    }
}
