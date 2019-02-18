package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.VipVisitor;

public class QueryVipConsumeResponse extends BaseResponse
{
    private int total;

    private StoreOrder[] storeOrders;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public StoreOrder[] getStoreOrders() {
        return storeOrders;
    }

    public void setStoreOrders(StoreOrder[] storeOrders) {
        this.storeOrders = storeOrders;
    }
}
