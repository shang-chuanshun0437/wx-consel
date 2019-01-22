package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.OrderInfo;
import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.StoreTable;
import com.weiyi.wx.order.dao.mapper.*;
import com.weiyi.wx.order.dao.request.GetStoreOrderListRequest;
import com.weiyi.wx.order.service.api.OrderInfoService;
import com.weiyi.wx.order.service.api.StoreOrderService;
import com.weiyi.wx.order.service.request.AddStoreOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang.math.RandomUtils;

import java.util.List;

@Service
public class StoreOrderServiceSpi implements StoreOrderService
{
    private Logger logger = LoggerFactory.getLogger(StoreOrderServiceSpi.class);

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreTableMapper storeTableMapper;

    @Autowired
    private StoreOrderMapper storeOrderMapper;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Transactional
    public void addStoreOrder(AddStoreOrderRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter addStoreOrder() func.the user is:{}",request.getUserPhone());
        }
        //校验该店铺是否属于该用户
        Store dbStore = storeMapper.queryStoreById(request.getStoreId());
        WxOrderAssert.isTrue(dbStore != null, ErrorCode.STORE_NOT_EXIST,"store not exist.");
        WxOrderAssert.isTrue(dbStore.getUserPhone().equals(request.getUserPhone()),ErrorCode.STORE_NOT_RIGHT,"the store not belong the user.");

        //校验餐桌是否存在
        StoreTable storeTable = new StoreTable();

        storeTable.setStoreId(request.getStoreId());
        storeTable.setTableId(request.getTableId());
        storeTable.setUserPhone(request.getUserPhone());

        StoreTable dbStoreTable = storeTableMapper.queryByTableIdAndStoreId(storeTable);
        WxOrderAssert.isTrue(dbStoreTable != null, ErrorCode.STORE_TABLE_NOT_EXIST,"store table not exist.");
        WxOrderAssert.isTrue(dbStoreTable.getStatus() == Constant.IDLE,ErrorCode.STORE_TABLE_USING,"table is using.");
        /*
         *构造订单参数
        */
        //订单生成时间
        String createTime = TimeUtil.getCurrentTime();
        //订单ID:当前时间+随机数
        String orderId = System.currentTimeMillis() + RandomUtils.nextInt(1000) + "";
        //订单总金额
        Double amount = 0.0;
        //会员金额
        Double vipAmount = 0.0;
        //实付金额
        Double realAmount = 0.0;

        for (int i = 0; i < request.getOrderInfos().length; i++){
            //订单详情
            OrderInfo orderInfo = request.getOrderInfos()[i];
            orderInfo.setCreateTime(createTime);
            orderInfo.setOrderId(orderId);
            orderInfo.setUserPhone(request.getUserPhone());
            orderInfo.setVipNum(request.getVipNum());
            orderInfo.setOrderType(Constant.ORDER_TYPE_FIRST);
            // 总金额
            orderInfo.setTotalPrice(orderInfo.getNewPrice() * orderInfo.getFoodCount());
            // vip总金额
            orderInfo.setVipTotalPrice(orderInfo.getVipPrice() * orderInfo.getFoodCount());

            //实收金额
            if (request.getPayType().intValue() == Constant.NO_PAY){
                orderInfo.setRealPrice(0.0);
            }else {
                Double realPrice = request.getVipNum() == null ?
                        orderInfo.getNewPrice() * orderInfo.getFoodCount() : orderInfo.getVipPrice() * orderInfo.getFoodCount();
                orderInfo.setRealPrice(realPrice);
            }

            //存入数据库
            orderInfoService.addOrderInfo(orderInfo);

            amount += orderInfo.getNewPrice() * orderInfo.getFoodCount();
            vipAmount += orderInfo.getVipPrice() * orderInfo.getFoodCount();
            realAmount += orderInfo.getRealPrice();
        }

        StoreOrder storeOrder = new StoreOrder();
        CopyProperties.copy(storeOrder,request);
        storeOrder.setOrderId(orderId);
        storeOrder.setCreateTime(createTime);
        storeOrder.setAmount(amount);
        storeOrder.setRealAmount(realAmount);
        storeOrder.setVipAmount(vipAmount);

        storeOrderMapper.addStoreOrder(storeOrder);

        //修改餐桌状态
        StoreTable updateStatus = new StoreTable();
        updateStatus.setUserPhone(request.getUserPhone());
        updateStatus.setStoreId(request.getStoreId());
        updateStatus.setTableId(request.getTableId());
        updateStatus.setStatus(Constant.DINNERING);
        updateStatus.setPersonNum(request.getPersonNum());
        storeTableMapper.updateStatusAndPerson(updateStatus);
    }

    @Transactional
    public void deleteStoreOrder(StoreOrder storeOrder) {
        if (logger.isDebugEnabled()){
            logger.debug("inter deleteStoreOrder() func.the user is:{}",storeOrder.getUserPhone());
        }
        StoreOrder dbStoreOrder = storeOrderMapper.queryStoreOrder(storeOrder);

        WxOrderAssert.isTrue(dbStoreOrder != null,ErrorCode.ORDER_NOT_EXIST,"order not exist.");
        WxOrderAssert.isTrue(dbStoreOrder.getPayType() == Constant.NO_PAY,ErrorCode.ORDER_HAVE_PAY,"order have pay.");

        //删除订单
        storeOrderMapper.deleteStoreOrder(storeOrder);

        //删除订单详情
        OrderInfo orderInfo = new OrderInfo();

        orderInfo.setUserPhone(storeOrder.getUserPhone());
        orderInfo.setOrderId(storeOrder.getOrderId());
        orderInfo.setStoreId(storeOrder.getStoreId());
        orderInfoMapper.deleteOrderInfo(orderInfo);

        //修改餐桌状态
        StoreTable updateStatus = new StoreTable();
        updateStatus.setUserPhone(storeOrder.getUserPhone());
        updateStatus.setStoreId(storeOrder.getStoreId());
        updateStatus.setTableId(storeOrder.getTableId());
        updateStatus.setStatus(Constant.IDLE);
        updateStatus.setPersonNum(0);
        storeTableMapper.updateStatusAndPerson(updateStatus);
    }

    @Transactional
    public void payStoreOrder(StoreOrder storeOrder) {
        if (logger.isDebugEnabled()){
            logger.debug("inter payStoreOrder() func.the user is:{}",storeOrder.getUserPhone());
        }
        //校验订单
        StoreOrder dbStoreOrder = storeOrderMapper.queryStoreOrder(storeOrder);
        WxOrderAssert.isTrue(dbStoreOrder != null,ErrorCode.ORDER_NOT_EXIST,"order not exist.");
        WxOrderAssert.isTrue(dbStoreOrder.getPayType() == Constant.NO_PAY,ErrorCode.ORDER_HAVE_PAY,"order have pay.");
        dbStoreOrder.setRealAmount(0.0);
        //校验订单详情
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserPhone(storeOrder.getUserPhone());
        orderInfo.setOrderId(storeOrder.getOrderId());
        orderInfo.setStoreId(storeOrder.getStoreId());
        List<OrderInfo> orderInfos = orderInfoMapper.queryOrderInfo(orderInfo);
        WxOrderAssert.isTrue(orderInfos != null && orderInfos.size() > 0,ErrorCode.ORDER_NOT_EXIST);

        //非会员结账
        if (storeOrder.getVipNum() == null || storeOrder.getVipNum() == ""){
            for (OrderInfo info: orderInfos){
                info.setRealPrice(info.getTotalPrice());
                orderInfoMapper.deleteOrderInfo(info);
                orderInfoMapper.addOrderInfo(info);
                dbStoreOrder.setRealAmount(dbStoreOrder.getRealAmount() + info.getTotalPrice());
            }
        }else {
            for (OrderInfo info: orderInfos){
                info.setRealPrice(info.getVipTotalPrice());
                info.setVipNum(storeOrder.getVipNum());
                orderInfoMapper.deleteOrderInfo(info);
                orderInfoMapper.addOrderInfo(info);
                dbStoreOrder.setRealAmount(dbStoreOrder.getRealAmount() + info.getVipTotalPrice());
                dbStoreOrder.setVipNum(storeOrder.getVipNum());
            }
        }
        //更新订单
        dbStoreOrder.setPayType(Constant.FRONT_DESK_PAY);
        storeOrderMapper.deleteStoreOrder(dbStoreOrder);
        storeOrderMapper.addStoreOrder(dbStoreOrder);

        //更新餐桌状态
        StoreTable storeTable = new StoreTable();
        storeTable.setUserPhone(storeOrder.getUserPhone());
        storeTable.setStoreId(storeOrder.getStoreId());
        storeTable.setTableId(storeOrder.getTableId());
        storeTable.setStatus(Constant.IDLE);
        storeTable.setPersonNum(0);
        storeTableMapper.updateStatusAndPerson(storeTable);
    }

    public List<StoreOrder> queryList(GetStoreOrderListRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryList() func.the user is:{}",request.getUserPhone());
        }

        return storeOrderMapper.queryList(request);
    }

    public int queryListCount(GetStoreOrderListRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryListCount() func.the user is:{}",request.getUserPhone());
        }

        return storeOrderMapper.queryListCount(request);
    }
}
