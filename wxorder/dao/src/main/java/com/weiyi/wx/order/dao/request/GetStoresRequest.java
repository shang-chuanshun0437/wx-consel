package com.weiyi.wx.order.dao.request;

import com.weiyi.wx.order.dao.entity.Store;

public class GetStoresRequest extends Store
{
    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
