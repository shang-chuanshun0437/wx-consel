package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.request.GetStoreOrderListRequest;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.*;
import com.weiyi.wx.order.response.*;
import com.weiyi.wx.order.service.api.StoreOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/store/order")
public class StoreOrderController
{
    private Logger logger = LoggerFactory.getLogger(StoreOrderController.class);

    @Autowired
    private StoreOrderService storeOrderService;

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
}
