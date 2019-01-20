package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.OrderInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderInfoMapper
{
    void addOrderInfo(OrderInfo orderInfo);

    void deleteOrderInfo(OrderInfo orderInfo);

    List<OrderInfo> queryOrderInfo(OrderInfo orderInfo);
}
