package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.Menu;

public class QueryMenuByIdResponse extends BaseResponse
{
    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
