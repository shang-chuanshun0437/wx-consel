package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.StoreTable;

public class QueryStoreTableResponse extends BaseResponse
{
    private int total;

    private StoreTable[] storeTables;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public StoreTable[] getStoreTables() {
        return storeTables;
    }

    public void setStoreTables(StoreTable[] storeTables) {
        this.storeTables = storeTables;
    }
}
