package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.entity.StoreTable;
import com.weiyi.wx.order.dao.request.GetStoreTablesRequest;
import com.weiyi.wx.order.dao.request.GetStoresRequest;

import java.util.List;

public interface StoreTableService
{
    void addStoreTable(StoreTable storeTable);

    void updateStoreTable(StoreTable storeTable);

    void deleteStoreTable(StoreTable storeTable);

    List<StoreTable> queryStoreTable(GetStoreTablesRequest getStoreTablesRequest);

    int queryStoreTableCount(GetStoreTablesRequest getStoreTablesRequest);
}
