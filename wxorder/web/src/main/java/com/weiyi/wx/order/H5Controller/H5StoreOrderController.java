package com.weiyi.wx.order.H5Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.rabbitmq.RabbitSendManage;
import com.weiyi.wx.order.common.utils.CopyProperties;
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

@Controller
@RequestMapping("/h5/store/order")
public class H5StoreOrderController
{
    private Logger logger = LoggerFactory.getLogger(H5StoreOrderController.class);

    @Autowired
    private StoreOrderService storeOrderService;

    /*
    *微信扫码订单：添加订单(前台收银)
    */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    public H5AddStoreOrderResponse h5AddStoreOrder(@RequestBody H5AddStoreOrderRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter h5AddStoreOrder() func,phone num:{}",request.getUserPhone());
        }

        H5AddStoreOrderResponse response = new H5AddStoreOrderResponse();
        Result result = new Result();
        response.setResult(result);

        com.weiyi.wx.order.service.request.AddStoreOrderRequest addStoreOrderRequest = new com.weiyi.wx.order.service.request.AddStoreOrderRequest();
        CopyProperties.copy(addStoreOrderRequest,request);

        try {
            String str = storeOrderService.addStoreOrder(addStoreOrderRequest);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

}
