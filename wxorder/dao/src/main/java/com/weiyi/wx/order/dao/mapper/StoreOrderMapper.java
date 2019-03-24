package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.request.GetStoreOrderListRequest;
import com.weiyi.wx.order.dao.request.GetWxOrderSalesRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreOrderMapper
{
    void addStoreOrder(StoreOrder storeOrder);

    void updateStoreOrderStatus(String orderId);

    void deleteStoreOrder(StoreOrder storeOrder);

    StoreOrder queryStoreOrder(StoreOrder storeOrder);

    List<StoreOrder> queryList(GetStoreOrderListRequest request);

    int queryListCount(GetStoreOrderListRequest request);

    Double queryWxOrderSales(GetWxOrderSalesRequest request);
}
