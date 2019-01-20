package com.weiyi.wx.order.dao.request;

import com.weiyi.wx.order.dao.entity.Menu;

public class GetMenuRequest extends Menu
{
    private Integer currentPage;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
