package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.StoreTable;
import com.weiyi.wx.order.dao.request.GetStoreTablesRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreTableMapper
{
    void addStoreTable(StoreTable storeTable);

    //根据商家手机号、店铺编号、餐桌编号，查询餐桌
    StoreTable queryByTableIdAndStoreId(StoreTable storeTable);

    //根据商家手机号、店铺编号、餐桌编号，删除餐桌
    void deleteTable(StoreTable storeTable);

    //更新餐桌状态
    void updateStatusAndPerson(StoreTable storeTable);

    List<StoreTable> queryTables(GetStoreTablesRequest getStoreTablesRequest);

    int queryTablesCount(GetStoreTablesRequest getStoreTablesRequest);
}
