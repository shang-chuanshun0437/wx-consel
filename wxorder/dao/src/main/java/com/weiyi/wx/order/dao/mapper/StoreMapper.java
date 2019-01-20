package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.request.GetStoresRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreMapper
{
    void addStore(Store store);

    List<Store> queryStores(GetStoresRequest getStoresRequest);

    Store queryStoreById(String storeId);

    int queryStoresCount(GetStoresRequest getStoresRequest);

    void updateStore(Store store);

    void deleteById(String storeId);
}
