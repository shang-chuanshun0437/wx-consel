package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.request.GetStoresRequest;

import java.util.List;

public interface StoreService
{
    void addStore(Store store);

    void deleteStore(Store store);

    void updateStore(Store store);

    List<Store> queryStores(GetStoresRequest getStoresRequest);

    Store queryStoreById(String id);

    int queryStoresCount(GetStoresRequest getStoresRequest);
}
