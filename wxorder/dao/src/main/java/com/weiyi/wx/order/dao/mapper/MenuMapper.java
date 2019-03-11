package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.Menu;
import com.weiyi.wx.order.dao.request.GetMenuRequest;
import com.weiyi.wx.order.dao.request.H5GetMenuRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuMapper
{
    void addMenu(Menu menu);

    void deleteMenu(Menu menu);

    //根据商家手机号、店铺编号、食物编号查询食物
    Menu queryById(Menu menu);

    List<Menu> queryMenu(GetMenuRequest getMenuRequest);

    //查询该店铺的所有菜单
    List<Menu> h5QueryMenu(H5GetMenuRequest h5GetMenuRequest);

    int queryMenuCount(GetMenuRequest getMenuRequest);

    void updateStatusAndRecommend(Menu menu);
}
