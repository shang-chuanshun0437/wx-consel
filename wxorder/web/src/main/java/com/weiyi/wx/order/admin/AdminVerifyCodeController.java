package com.weiyi.wx.order.admin;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.entity.VerifyCode;
import com.weiyi.wx.order.dao.request.QueryVerifyCodeListReq;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.QueryVerifyCodeRequest;
import com.weiyi.wx.order.response.QueryVerifyCodeResponse;
import com.weiyi.wx.order.service.api.VerifyCodeService;
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
@RequestMapping("/verifyCode")
public class AdminVerifyCodeController
{
    private Logger logger = LoggerFactory.getLogger(AdminVerifyCodeController.class);

    @Autowired
    private VerifyCodeService verifyCodeService;

    @RequestMapping(value = "/query/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public QueryVerifyCodeResponse queryCode(@RequestBody QueryVerifyCodeRequest request)
    {
        QueryVerifyCodeResponse response = new QueryVerifyCodeResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryCode() func ,the request:{}",request);
        }

        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }

        //查询数量
        QueryVerifyCodeListReq queryVerifyCodeListReq = new QueryVerifyCodeListReq();
        CopyProperties.copy(queryVerifyCodeListReq,request);
        queryVerifyCodeListReq.setUserPhone(request.getNeedPhone());
        queryVerifyCodeListReq.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        int total = verifyCodeService.queryVerifyCodeInfoCount(queryVerifyCodeListReq);

        response.setCount(total);

        if (total <= 0)
        {
            return response;
        }

        //查询详情
        List<VerifyCode> verifyCodes = verifyCodeService.queryVerifyCodeInfo(queryVerifyCodeListReq);
        if (verifyCodes != null && verifyCodes.size() > 0)
        {
            response.setVerifyCodes(verifyCodes.toArray(new VerifyCode[verifyCodes.size()]));
        }
        return response;
    }
}
