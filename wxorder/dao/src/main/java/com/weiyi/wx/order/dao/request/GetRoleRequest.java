package com.weiyi.wx.order.dao.request;

import com.weiyi.wx.order.dao.entity.Role;

public class GetRoleRequest extends Role
{
    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
