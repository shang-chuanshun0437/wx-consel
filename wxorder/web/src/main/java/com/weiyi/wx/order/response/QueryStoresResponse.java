package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.Store;

public class QueryStoresResponse extends BaseResponse
{
    private Store[] stores;

    private int total;

    public Store[] getStores() {
        return stores;
    }

    public void setStores(Store[] stores) {
        this.stores = stores;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
