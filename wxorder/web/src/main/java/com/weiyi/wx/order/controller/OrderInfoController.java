package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.entity.OrderInfo;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.*;
import com.weiyi.wx.order.response.*;
import com.weiyi.wx.order.service.api.OrderInfoService;
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
@RequestMapping("/store/order/info")
public class OrderInfoController
{
    private Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    @Autowired
    private OrderInfoService orderInfoService;

    /*
    *查询订单详情
    */
    @RequestMapping(value = "/query",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryOrderInfoResponse queryOrderInfo(@RequestBody QueryOrderInfoRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryOrderInfo() func,phone num:{}",request.getUserPhone());
        }

        QueryOrderInfoResponse response = new QueryOrderInfoResponse();
        Result result = new Result();
        response.setResult(result);

        OrderInfo orderInfo = new OrderInfo();
        CopyProperties.copy(orderInfo,request);

        List<OrderInfo> orderInfos = orderInfoService.queryOrderInfo(orderInfo);
        if (orderInfos != null && orderInfos.size() > 0){
            response.setTotal(orderInfos.size());
            response.setOrderInfos(orderInfos.toArray(new OrderInfo[orderInfos.size()]));
        }
        return response;
    }

    /*
     *删除订单中的一个商品
     */
    @RequestMapping(value = "/delete",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public DeleteOrderInfoResponse deleteOrderInfo(@RequestBody DeleteOrderInfoRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteOrderInfo() func,phone num:{}",request.getUserPhone());
        }

        DeleteOrderInfoResponse response = new DeleteOrderInfoResponse();
        Result result = new Result();
        response.setResult(result);

        OrderInfo orderInfo = new OrderInfo();
        CopyProperties.copy(orderInfo,request);

        try {
            orderInfoService.deleteOrderInfo(orderInfo);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *向订单中添加一个商品
     */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AddOrderInfoResponse addOrderInfo(@RequestBody AddOrderInfoRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addOrderInfo() func,phone num:{}",request.getUserPhone());
        }

        AddOrderInfoResponse response = new AddOrderInfoResponse();
        Result result = new Result();
        response.setResult(result);

        com.weiyi.wx.order.service.request.AddOrderInfoRequest addOrderInfoRequest = new com.weiyi.wx.order.service.request.AddOrderInfoRequest();

        CopyProperties.copy(addOrderInfoRequest,request);

        try {
            orderInfoService.addOrderInfoByOrderId(addOrderInfoRequest);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }
}
