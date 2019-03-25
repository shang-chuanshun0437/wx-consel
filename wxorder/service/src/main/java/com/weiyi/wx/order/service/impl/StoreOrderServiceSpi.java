package com.weiyi.wx.order.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.rabbitmq.RabbitSendManage;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.*;
import com.weiyi.wx.order.dao.mapper.*;
import com.weiyi.wx.order.dao.request.GetStoreOrderListRequest;
import com.weiyi.wx.order.dao.request.GetWxOrderSalesRequest;
import com.weiyi.wx.order.service.api.OrderInfoService;
import com.weiyi.wx.order.service.api.PaySettingService;
import com.weiyi.wx.order.service.api.StoreOrderService;
import com.weiyi.wx.order.service.request.AddStoreOrderRequest;
import com.weiyi.wx.order.service.wxpay.WXPay;
import com.weiyi.wx.order.service.wxpay.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang.math.RandomUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StoreOrderServiceSpi implements StoreOrderService
{
    private Logger logger = LoggerFactory.getLogger(StoreOrderServiceSpi.class);

    public static Map<String,AddStoreOrderRequest> addStoreOrderRequestMap = new ConcurrentHashMap<String,AddStoreOrderRequest>();

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

    @Autowired
    private VipVisitorMapper vipVisitorMapper;

    @Autowired
    private PaySettingService paySettingService;

    public Map<String, String> addStoreOrder(AddStoreOrderRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter addStoreOrder() func.the user is:{}",request.getUserPhone());
        }
        //校验店铺和餐桌是否存在
        this.checkOrder(request);

        //如果是扫码点餐---并且是前台支付(即为非临时订单)，则放入消息队列
        if (request.getSource() == Constant.ORDER_SOURCE_APP && request.getOrderTemp() == 2){
            try {
                String jsonString = JSONObject.toJSONString(request);

                //存入消息队列
                RabbitSendManage.topicSendMsg(JSONObject.parseObject(jsonString));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else if (request.getSource() == Constant.ORDER_SOURCE_APP &&
                request.getOrderTemp() == 1 && request.getPayType() == Constant.WEI_XI_PAY){
            //如果是扫码点餐&临时订单&微信在线支付，则调微信预支付接口
            String orderId = System.currentTimeMillis() + RandomUtils.nextInt(1000) + "";

            return wxPreparePay(request.getTotalAmount() * 100,orderId,request.getUserPhone());

        } else {
            createOrder(request);
        }
        return null;
    }

    @Override
    public void updateStoreOrderStatus(String orderId) {
        if (logger.isDebugEnabled()){
            logger.debug("inter updateStoreOrderStatus() func.the orderId is:{}",orderId);
        }
        storeOrderMapper.updateStoreOrderStatus(orderId);
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
            //会员结账   查询该会员是否存在
            VipVisitor vipVisitor = new VipVisitor();
            vipVisitor.setUserPhone(storeOrder.getUserPhone());
            vipVisitor.setVipId(storeOrder.getVipNum());

            VipVisitor dbVipVisitor = vipVisitorMapper.queryVip(vipVisitor);
            WxOrderAssert.isTrue(dbVipVisitor != null,ErrorCode.VIP_NOT_EXIST,"vip not exist.");

            dbVipVisitor.setConsumCount(dbVipVisitor.getConsumCount() + 1);
            dbVipVisitor.setUpdateTime(TimeUtil.getCurrentTime());

            vipVisitorMapper.deleteVip(dbVipVisitor);
            vipVisitorMapper.addVip(dbVipVisitor);

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

    public double queryWxOrderSales(GetWxOrderSalesRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryWxOrderSales() func.the user is:{}",request.getUserPhone());
        }
        Double db = storeOrderMapper.queryWxOrderSales(request);
        return db == null ? 0.0 : db;
    }

    //返回订单ID
    @Transactional
    public void createOrder(AddStoreOrderRequest request){
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
        storeOrder.setVipNum(request.getVipNum());

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

    private void checkOrder(AddStoreOrderRequest request){
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

        if (request.getSource() == Constant.ORDER_SOURCE_FRONT){
            WxOrderAssert.isTrue(dbStoreTable.getStatus() == Constant.IDLE,ErrorCode.STORE_TABLE_USING,"table is using.");
        }
    }

    private Map<String, String> wxPreparePay(Double totalFee,String orderId,Long userPhone) {
        //页面获取openId接口
        //String getopenid_url = https://api.weixin.qq.com/sns/oauth2/access_token;
        //String  param = "appid="+你appid+"&secret="+你secret+"&code="+code+"&grant_type=authorization_code";
        //向微信服务器发送get请求获取openIdStr
        //String openIdStr = HttpRequest.sendGet(getopenid_url, param);
        //JSONObject json = JSONObject.parseObject(openIdStr);//转成Json格式
        //String openId = json.getString("openid");//获取openId
        //从数据库中获取用户的微信支付参数
        PaySetting dbPaySetting = paySettingService.queryPaySetting(userPhone);
        WxOrderAssert.isTrue(dbPaySetting != null,ErrorCode.USER_NOT_EXIST,"user not exist.");
        WXPay wxPay = new WXPay(dbPaySetting.getAppId(),dbPaySetting.getMchId(),dbPaySetting.getOpenId(),orderId,
                dbPaySetting.getWxKey(),totalFee);

        Map<String, String> result = wxPay.preparePay();
        Map<String, String> payMap = null;

        if (result != null && result.get("return_code ") == "SUCCESS" && result.get("result_code ") == "SUCCESS"){
            try {
                //在这种情况下才会返回prepay_id
                payMap = new HashMap<String, String>();
                payMap.put("appId", dbPaySetting.getAppId());
                payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp()+"");
                payMap.put("nonceStr", WXPayUtil.generateNonceStr());
                payMap.put("signType", "MD5");
                payMap.put("package", "prepay_id=" + result.get("prepay_id"));
                String paySign = WXPayUtil.generateSignature(payMap, dbPaySetting.getWxKey());
                payMap.put("paySign", paySign);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return payMap;
    }
}
