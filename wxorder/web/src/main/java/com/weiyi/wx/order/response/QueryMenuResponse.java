package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.Menu;

public class QueryMenuResponse extends BaseResponse
{
    private int total;

    private Menu[] menus;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Menu[] getMenus() {
        return menus;
    }

    public void setMenus(Menu[] menus) {
        this.menus = menus;
    }
}
