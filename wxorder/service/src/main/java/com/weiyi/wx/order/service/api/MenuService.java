package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.Menu;
import com.weiyi.wx.order.dao.request.GetMenuRequest;
import com.weiyi.wx.order.dao.request.H5GetMenuRequest;

import java.util.List;

public interface MenuService
{
    void addMenu(Menu menu);

    List<Menu> queryMenu(GetMenuRequest getMenuRequest);

    int queryMenuCount(GetMenuRequest getMenuRequest);

    void deleteMenu(Menu menu);

    void updateMenu(Menu menu);

    Menu queryMenuById(Menu menu);

    void updateStatusAndRecommend(Menu menu);

    List<Menu> h5QueryMenu(H5GetMenuRequest h5GetMenuRequest);
}
