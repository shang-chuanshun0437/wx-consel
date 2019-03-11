package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.utils.FileFactory;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.Menu;
import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.entity.StoreTable;
import com.weiyi.wx.order.dao.mapper.MenuMapper;
import com.weiyi.wx.order.dao.mapper.StoreMapper;
import com.weiyi.wx.order.dao.mapper.StoreTableMapper;
import com.weiyi.wx.order.dao.request.GetMenuRequest;
import com.weiyi.wx.order.dao.request.H5GetMenuRequest;
import com.weiyi.wx.order.service.api.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceSpi implements MenuService
{
    private Logger logger = LoggerFactory.getLogger(MenuServiceSpi.class);

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private StoreTableMapper storeTableMapper;

    public void addMenu(Menu menu) {
        if (logger.isDebugEnabled()){
            logger.debug("inter addMenu() func.the user is:{}",menu.getUserPhone());
        }
        //校验该店铺是否属于该用户
        Store dbStore = storeMapper.queryStoreById(menu.getStoreId());
        WxOrderAssert.isTrue(dbStore != null, ErrorCode.STORE_NOT_EXIST,"store not exist.");
        WxOrderAssert.isTrue(dbStore.getUserPhone().equals(menu.getUserPhone()),ErrorCode.STORE_NOT_RIGHT,"the store not belong the user.");

        //校验食物编号是否存在
        Menu dbMenu = menuMapper.queryById(menu);
        WxOrderAssert.isTrue(dbMenu == null,ErrorCode.FOOD_EXIST,"food already exist.");

        //添加食物
        menu.setCreateTime(TimeUtil.getCurrentTime());
        menu.setStatus(Constant.NOT_SELL_OUT);

        menuMapper.addMenu(menu);
    }

    public List<Menu> queryMenu(GetMenuRequest getMenuRequest) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryMenu() func.the user is:{}",getMenuRequest.getUserPhone());
        }
        return menuMapper.queryMenu(getMenuRequest);
    }

    public int queryMenuCount(GetMenuRequest getMenuRequest) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryMenuCount() func.the user is:{}",getMenuRequest.getUserPhone());
        }
        return menuMapper.queryMenuCount(getMenuRequest);
    }

    public void deleteMenu(Menu menu) {
        if (logger.isDebugEnabled()){
            logger.debug("inter deleteMenu() func.the user is:{}",menu.getUserPhone());
        }
        //从数据库中查询menu
        Menu dbMenu = menuMapper.queryById(menu);
        WxOrderAssert.isTrue(dbMenu != null,ErrorCode.FOOD_NOT_EXIST,"the food not exist.");

        menuMapper.deleteMenu(dbMenu);

        //删除图片
        FileFactory.delFile(Constant.FOOD_IMG_DIR_ROOT + FileFactory.getFileNameWithTime(dbMenu.getFoodImg()));
    }

    @Transactional
    public void updateMenu(Menu menu) {
        if (logger.isDebugEnabled()){
            logger.debug("inter updateMenu() func.the user is:{}",menu.getUserPhone());
        }

        //从数据库中查询menu
        Menu dbMenu = menuMapper.queryById(menu);
        WxOrderAssert.isTrue(dbMenu != null,ErrorCode.FOOD_NOT_EXIST,"the food not exist.");

        //先删除再插入
        menuMapper.deleteMenu(dbMenu);

        menu.setCreateTime(dbMenu.getCreateTime());
        addMenu(menu);

        //如果修改了商品图片则删除之前的商品图片
        if (!dbMenu.getFoodImg().equals(menu.getFoodImg())){
            FileFactory.delFile(Constant.FOOD_IMG_DIR_ROOT + FileFactory.getFileNameWithTime(dbMenu.getFoodImg()));
        }
    }

    public Menu queryMenuById(Menu menu) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryMenuById() func.the user is:{}",menu.getUserPhone());
        }
        return menuMapper.queryById(menu);
    }

    public void updateStatusAndRecommend(Menu menu) {
        if (logger.isDebugEnabled()){
            logger.debug("inter updateStatusAndRecommend() func.the user is:{}",menu.getUserPhone());
        }

        menuMapper.updateStatusAndRecommend(menu);
    }

    public List<Menu> h5QueryMenu(H5GetMenuRequest h5GetMenuRequest) {
        if (logger.isDebugEnabled()){
            logger.debug("inter h5QueryMenu() func.the h5GetMenuRequest is:{}",h5GetMenuRequest);
        }
        //首先判断桌号是否存在
        StoreTable storeTable = new StoreTable();

        storeTable.setUserPhone(h5GetMenuRequest.getUserPhone());
        storeTable.setStoreId(h5GetMenuRequest.getStoreId());
        storeTable.setTableId(h5GetMenuRequest.getTableId());

        StoreTable dbStoreTable = storeTableMapper.queryByTableIdAndStoreId(storeTable);
        WxOrderAssert.isTrue(dbStoreTable != null,ErrorCode.STORE_TABLE_NOT_EXIST,"store table not exist.");

        //查询该店铺的所有菜单
        return menuMapper.h5QueryMenu(h5GetMenuRequest);
    }
}
