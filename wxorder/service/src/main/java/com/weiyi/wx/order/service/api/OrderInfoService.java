package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.OrderInfo;
import com.weiyi.wx.order.dao.request.GetStoreOrderInfoListRequest;
import com.weiyi.wx.order.service.request.AddOrderInfoRequest;

import java.util.List;

public interface OrderInfoService
{
    void addOrderInfo(OrderInfo orderInfo);

    void addOrderInfoByOrderId(AddOrderInfoRequest addOrderInfoRequest);

    void deleteOrderInfo(OrderInfo orderInfo);

    List<OrderInfo> queryOrderInfo(OrderInfo orderInfo);

    List<OrderInfo> queryOrderInfoByStoreId(GetStoreOrderInfoListRequest orderInfo);
}
