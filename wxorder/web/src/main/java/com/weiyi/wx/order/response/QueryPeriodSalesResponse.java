package com.weiyi.wx.order.response;

import com.weiyi.wx.order.domain.SalesStatistics;

public class QueryPeriodSalesResponse extends BaseResponse
{
    private SalesStatistics allSales;

    private SalesStatistics []eachStoreSales;

    public SalesStatistics getAllSales() {
        return allSales;
    }

    public void setAllSales(SalesStatistics allSales) {
        this.allSales = allSales;
    }

    public SalesStatistics[] getEachStoreSales() {
        return eachStoreSales;
    }

    public void setEachStoreSales(SalesStatistics[] eachStoreSales) {
        this.eachStoreSales = eachStoreSales;
    }
}
