package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.request.GetStoreOrderListRequest;
import com.weiyi.wx.order.dao.request.GetWxOrderSalesRequest;
import com.weiyi.wx.order.service.request.AddStoreOrderRequest;

import java.util.List;
import java.util.Map;

public interface StoreOrderService
{
    Map<String, String> addStoreOrder(AddStoreOrderRequest request);

    void updateStoreOrderStatus(String orderId);

    void deleteStoreOrder(StoreOrder storeOrder);

    void payStoreOrder(StoreOrder storeOrder);

    List<StoreOrder> queryList(GetStoreOrderListRequest request);

    int queryListCount(GetStoreOrderListRequest request);

    double queryWxOrderSales(GetWxOrderSalesRequest request);

    void createOrder(AddStoreOrderRequest request);
}
