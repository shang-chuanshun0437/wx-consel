package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.OrderInfo;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.request.GetStoreOrderInfoListRequest;
import com.weiyi.wx.order.dao.request.GetStoreOrderListRequest;
import com.weiyi.wx.order.dao.request.GetWxOrderSalesRequest;
import com.weiyi.wx.order.domain.Statistics;
import com.weiyi.wx.order.domain.StatisticsPercent;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.*;
import com.weiyi.wx.order.response.*;
import com.weiyi.wx.order.service.api.OrderInfoService;
import com.weiyi.wx.order.service.api.StoreOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("/store/order")
public class StoreOrderController
{
    private Logger logger = LoggerFactory.getLogger(StoreOrderController.class);

    @Autowired
    private StoreOrderService storeOrderService;

    @Autowired
    private OrderInfoService orderInfoService;
    /*
    *添加订单
    */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AddStoreOrderResponse addStoreOrder(@RequestBody AddStoreOrderRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addStoreOrder() func,phone num:{}",request.getUserPhone());
        }

        AddStoreOrderResponse response = new AddStoreOrderResponse();
        Result result = new Result();
        response.setResult(result);

        com.weiyi.wx.order.service.request.AddStoreOrderRequest addStoreOrderRequest = new com.weiyi.wx.order.service.request.AddStoreOrderRequest();
        CopyProperties.copy(addStoreOrderRequest,request);

        try {
            storeOrderService.addStoreOrder(addStoreOrderRequest);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }
    /*
     *更新订单状态
     */
    @RequestMapping(value = "/update/status",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateStoreOrderStatusResponse updateStoreOrderStatus(@RequestBody UpdateStoreOrderStatusRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateStoreOrderStatus() func,phone num:{}",request.getUserPhone());
        }

        UpdateStoreOrderStatusResponse response = new UpdateStoreOrderStatusResponse();
        Result result = new Result();
        response.setResult(result);

        storeOrderService.updateStoreOrderStatus(request.getOrderId());

        return response;
    }
    /*
     *查询订单
     */
    @RequestMapping(value = "/query/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryStoreOrderResponse queryStoreOrder(@RequestBody QueryStoreOrderRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryStoreOrder() func,phone num:{}",request.getUserPhone());
        }

        QueryStoreOrderResponse response = new QueryStoreOrderResponse();
        Result result = new Result();
        response.setResult(result);

        if (request.getCurrentPage() != null){
            request.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        }
        GetStoreOrderListRequest getStoreOrderListRequest = new GetStoreOrderListRequest();

        CopyProperties.copy(getStoreOrderListRequest,request);

        try {
            int total = storeOrderService.queryListCount(getStoreOrderListRequest);
            List<StoreOrder> storeOrders = storeOrderService.queryList(getStoreOrderListRequest);
            if (storeOrders != null && storeOrders.size() > 0){
                response.setStoreOrders(storeOrders.toArray(new StoreOrder[storeOrders.size()]));
            }
            response.setTotal(total);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *删除订单
     */
    @RequestMapping(value = "/delete",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public DeleteStoreOrderResponse deleteStoreOrder(@RequestBody DeleteStoreOrderRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteStoreOrder() func,phone num:{}",request.getUserPhone());
        }

        DeleteStoreOrderResponse response = new DeleteStoreOrderResponse();
        Result result = new Result();
        response.setResult(result);

        StoreOrder storeOrder = new StoreOrder();
        CopyProperties.copy(storeOrder,request);

        try {
            storeOrderService.deleteStoreOrder(storeOrder);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *结账
     */
    @RequestMapping(value = "/pay",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public PayStoreOrderResponse payStoreOrder(@RequestBody PayStoreOrderRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter payStoreOrder() func,phone num:{}",request.getUserPhone());
        }

        PayStoreOrderResponse response = new PayStoreOrderResponse();
        Result result = new Result();
        response.setResult(result);

        StoreOrder storeOrder = new StoreOrder();
        CopyProperties.copy(storeOrder,request);

        try {
            storeOrderService.payStoreOrder(storeOrder);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *统计店铺今日销售额度
     */
    @RequestMapping(value = "/statistic",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public StoreTodaySalesResponse storeTodaySales(@RequestBody StoreTodaySalesRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter storeTodaySales() func,phone num:{}",request.getUserPhone());
        }

        StoreTodaySalesResponse response = new StoreTodaySalesResponse();
        Result result = new Result();
        response.setResult(result);

        String beginTime = TimeUtil.getDate(TimeUtil.getCurrentTime());
        Map<String,Double> allMap = new HashMap<String, Double>();
        allMap.put(beginTime,0.0);
        //初始化类目map
        Map<Integer,Double> categoryMap = new HashMap<Integer, Double>();
        initMap(categoryMap);

        GetStoreOrderInfoListRequest getStoreOrderInfoListRequest = new GetStoreOrderInfoListRequest();

        getStoreOrderInfoListRequest.setUserPhone(request.getUserPhone());
        getStoreOrderInfoListRequest.setStoreId(request.getStoreId());
        getStoreOrderInfoListRequest.setBeginTime(beginTime + " 00:00:00");
        getStoreOrderInfoListRequest.setEndTime(beginTime + " 23:59:59");

        double total = 0.0;
        List<OrderInfo> orderInfos = orderInfoService.queryOrderInfoByStoreId(getStoreOrderInfoListRequest);
        if (orderInfos != null && orderInfos.size() > 0){
            for (OrderInfo orderInfo: orderInfos){
                total += orderInfo.getRealPrice();
                double temp = categoryMap.get(orderInfo.getCategory());
                categoryMap.put(orderInfo.getCategory(),temp + orderInfo.getRealPrice());
            }
            allMap.put(TimeUtil.getDate(TimeUtil.getCurrentTime()),total);
        }
        //解析map
        List<Statistics<Double>> allStatistics = analysisMap(allMap);
        response.setAllStatistics(allStatistics.toArray(new Statistics[allStatistics.size()]));

        List<Statistics<Double>> categoryStatistics = convertMap(categoryMap);
        response.setCategoryStatistics(categoryStatistics.toArray(new Statistics[categoryStatistics.size()]));

        //统计扫码点餐和前台点餐比例:保留两位小数
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        double wxPercent = 1.00;
        if (total != 0){
            GetWxOrderSalesRequest getWxOrderSalesRequest = new GetWxOrderSalesRequest();
            getWxOrderSalesRequest.setBeginTime(beginTime + " 00:00:00");
            getWxOrderSalesRequest.setEndTime(beginTime + " 23:59:59");
            getWxOrderSalesRequest.setUserPhone(request.getUserPhone());
            getWxOrderSalesRequest.setStoreId(request.getStoreId());

            double wxTotal = storeOrderService.queryWxOrderSales(getWxOrderSalesRequest);
            wxPercent = Double.valueOf(decimalFormat.format(wxTotal / total));
        }
        List<StatisticsPercent> statisticsPercentList = new ArrayList<StatisticsPercent>(2);

        StatisticsPercent statisticsPercentVip = new StatisticsPercent();
        statisticsPercentVip.setItem("扫码点餐");
        statisticsPercentVip.setPercent(wxPercent);

        StatisticsPercent statisticsPercentNormal = new StatisticsPercent();
        statisticsPercentNormal.setItem("前台点餐");
        statisticsPercentNormal.setPercent(1 - wxPercent);

        statisticsPercentList.add(statisticsPercentVip);
        statisticsPercentList.add(statisticsPercentNormal);

        response.setStatisticsPercents(statisticsPercentList.toArray(new StatisticsPercent[statisticsPercentList.size()]));
        return response;
    }

    private void initMap(Map<Integer,Double> map){
        //共有7个类目
        for (int i = 1;i <=7;i++){
            map.put(i,0.0);
        }
    }
    //遍历map，转换成相应的类
    private List<Statistics<Double>> analysisMap(Map<String,Double> map)
    {
        Iterator iterator = map.entrySet().iterator();
        List<Statistics<Double>> statisticsList = new ArrayList<Statistics<Double>>();
        while(iterator.hasNext())
        {
            Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iterator.next();
            Statistics<Double> statistics = new Statistics<Double>();

            statistics.setYear(entry.getKey());
            statistics.setValue(entry.getValue());

            statisticsList.add(statistics);
        }
        return statisticsList;
    }

    //将类目编号转换成汉字，然后转换成相应的类
    private List<Statistics<Double>> convertMap(Map<Integer,Double> map)
    {
        Iterator iterator = map.entrySet().iterator();
        List<Statistics<Double>> statisticsList = new ArrayList<Statistics<Double>>();
        while(iterator.hasNext())
        {
            Map.Entry<Integer, Double> entry = (Map.Entry<Integer, Double>) iterator.next();
            Statistics<Double> statistics = new Statistics<Double>();

            switch (entry.getKey()){
                case 1:
                    statistics.setYear("小炒系列");
                    break;
                case 2:
                    statistics.setYear("凉菜系列");
                    break;
                case 3:
                    statistics.setYear("海鲜系列");
                    break;
                case 4:
                    statistics.setYear("汤系列");
                    break;
                case 5:
                    statistics.setYear("酒水饮料");
                    break;
                case 6:
                    statistics.setYear("主食");
                    break;
                case 7:
                    statistics.setYear("其它");
                    break;
                default:
                    statistics.setYear("未知");
            }
            statistics.setValue(entry.getValue());

            statisticsList.add(statistics);
        }
        return statisticsList;
    }
}
