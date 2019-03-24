package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.OrderInfo;
import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.mapper.OrderInfoMapper;
import com.weiyi.wx.order.dao.mapper.StoreOrderMapper;
import com.weiyi.wx.order.dao.request.GetStoreOrderInfoListRequest;
import com.weiyi.wx.order.service.api.OrderInfoService;
import com.weiyi.wx.order.service.request.AddOrderInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderInfoServiceSpi implements OrderInfoService
{
    private Logger logger = LoggerFactory.getLogger(OrderInfoServiceSpi.class);

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private StoreOrderMapper storeOrderMapper;

    public void addOrderInfo(OrderInfo orderInfo) {
        if (logger.isDebugEnabled()){
            logger.debug("into addOrderInfo,the order id is:{}",orderInfo.getOrderId());
        }
        orderInfoMapper.addOrderInfo(orderInfo);
    }

    @Transactional
    public void addOrderInfoByOrderId(AddOrderInfoRequest addOrderInfoRequest) {
        if (logger.isDebugEnabled()){
            logger.debug("into addOrderInfoByOrderId,the order id is:{}",addOrderInfoRequest.getOrderId());
        }
        //根据orderId去查询该订单是否存在
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setUserPhone(addOrderInfoRequest.getUserPhone());
        storeOrder.setStoreId(addOrderInfoRequest.getStoreId());
        storeOrder.setOrderId(addOrderInfoRequest.getOrderId());

        StoreOrder dbStoreOrder = storeOrderMapper.queryStoreOrder(storeOrder);
        WxOrderAssert.isTrue(dbStoreOrder != null,ErrorCode.ORDER_NOT_EXIST,"order not exist.");
        WxOrderAssert.isTrue(dbStoreOrder.getPayType() == Constant.NO_PAY,ErrorCode.ORDER_HAVE_PAY,"order have pay.");

        //更新订单数据
        //订单总金额
        Double amount = dbStoreOrder.getAmount();
        //会员金额
        Double vipAmount = dbStoreOrder.getVipAmount();

        //将订单详情存入数据库
        for (OrderInfo orderInfo : addOrderInfoRequest.getOrderInfos()){
            orderInfo.setOrderType(Constant.ORDER_TYPE_SECOND);
            orderInfo.setCreateTime(TimeUtil.getCurrentTime());
            orderInfo.setUserPhone(dbStoreOrder.getUserPhone());
            orderInfo.setStoreId(dbStoreOrder.getStoreId());
            orderInfo.setOrderId(dbStoreOrder.getOrderId());
            orderInfo.setRealPrice(0.0);
            orderInfo.setTotalPrice(orderInfo.getNewPrice() * orderInfo.getFoodCount());
            orderInfo.setVipTotalPrice(orderInfo.getVipPrice() * orderInfo.getFoodCount());
            amount += orderInfo.getNewPrice() * orderInfo.getFoodCount();
            vipAmount += orderInfo.getVipPrice() * orderInfo.getFoodCount();
            orderInfoMapper.addOrderInfo(orderInfo);
        }
        //更新订单
        dbStoreOrder.setAmount(amount);
        dbStoreOrder.setVipAmount(vipAmount);

        storeOrderMapper.deleteStoreOrder(dbStoreOrder);
        storeOrderMapper.addStoreOrder(dbStoreOrder);
    }

    @Transactional
    public void deleteOrderInfo(OrderInfo orderInfo) {
        if (logger.isDebugEnabled()){
            logger.debug("into deleteOrderInfo,the order id is:{}",orderInfo.getOrderId());
        }
        //根据orderId和foodId查询订单详情

        //查询该订单
        StoreOrder storeOrder = new StoreOrder();

        storeOrder.setUserPhone(orderInfo.getUserPhone());
        storeOrder.setStoreId(orderInfo.getStoreId());
        storeOrder.setOrderId(orderInfo.getOrderId());

        StoreOrder dbStoreOrder = storeOrderMapper.queryStoreOrder(storeOrder);
        WxOrderAssert.isTrue(dbStoreOrder != null, ErrorCode.ORDER_NOT_EXIST,"order not exist.");
        WxOrderAssert.isTrue(dbStoreOrder.getPayType() == Constant.NO_PAY,ErrorCode.ORDER_HAVE_PAY,"order have pay.");

        //更新订单
        dbStoreOrder.setVipAmount(dbStoreOrder.getVipAmount() - orderInfo.getVipTotalPrice());
        dbStoreOrder.setAmount(dbStoreOrder.getAmount() - orderInfo.getTotalPrice());
        dbStoreOrder.setRealAmount(0.0);
        storeOrderMapper.deleteStoreOrder(storeOrder);
        storeOrderMapper.addStoreOrder(dbStoreOrder);

        //更新订单详情
        orderInfoMapper.deleteOrderInfo(orderInfo);
    }

    public List<OrderInfo> queryOrderInfo(OrderInfo orderInfo) {
        if (logger.isDebugEnabled()){
            logger.debug("into queryOrderInfo,the order id is:{}",orderInfo.getOrderId());
        }
        return orderInfoMapper.queryOrderInfo(orderInfo);
    }

    public List<OrderInfo> queryOrderInfoByStoreId(GetStoreOrderInfoListRequest getStoreOrderInfoListRequest) {
        if (logger.isDebugEnabled()){
            logger.debug("into queryOrderInfoByStoreId,the order id is:{}",getStoreOrderInfoListRequest.getStoreId());
        }
        return orderInfoMapper.queryOrderInfoByStoreId(getStoreOrderInfoListRequest);
    }
}
