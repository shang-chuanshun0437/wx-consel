package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.request.GetStoreOrderListRequest;
import com.weiyi.wx.order.service.request.AddStoreOrderRequest;

import java.util.List;

public interface StoreOrderService
{
    void addStoreOrder(AddStoreOrderRequest request);

    void deleteStoreOrder(StoreOrder storeOrder);

    void payStoreOrder(StoreOrder storeOrder);

    List<StoreOrder> queryList(GetStoreOrderListRequest request);

    int queryListCount(GetStoreOrderListRequest request);
}
