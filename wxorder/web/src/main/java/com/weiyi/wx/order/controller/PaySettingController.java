package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.entity.PaySetting;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.*;
import com.weiyi.wx.order.response.*;
import com.weiyi.wx.order.service.api.PaySettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/*
* 商家操作
*/
@Controller
@RequestMapping("/user")
public class PaySettingController
{
    private Logger logger = LoggerFactory.getLogger(PaySettingController.class);

    @Autowired
    private PaySettingService paySettingService;

    /*
     *查询用户支付设置
     */
    @RequestMapping(value = "/query/paySetting",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryUserPaySettingResponse queryPaySetting(@RequestBody QueryUserPaySettingRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryPaySetting() func,phone num:{}",request.getUserPhone());
        }

        QueryUserPaySettingResponse response = new QueryUserPaySettingResponse();
        Result result = new Result();
        response.setResult(result);

        PaySetting paySetting = paySettingService.queryPaySetting(request.getUserPhone());

        response.setPaySetting(paySetting);
        return response;
    }

    /*
     *更新用户支付设置
     */
    @RequestMapping(value = "/update/paySetting",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateUserPaySettingResponse updatePaySetting(@RequestBody UpdateUserPaySettingRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryPaySetting() func,phone num:{}",request.getUserPhone());
        }

        UpdateUserPaySettingResponse response = new UpdateUserPaySettingResponse();
        Result result = new Result();
        response.setResult(result);

        PaySetting paySetting = new PaySetting();
        CopyProperties.copy(paySetting,request);

        paySettingService.updatePaySetting(paySetting);

        return response;
    }
}
