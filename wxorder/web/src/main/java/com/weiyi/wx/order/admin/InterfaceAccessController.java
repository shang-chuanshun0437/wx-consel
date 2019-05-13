package com.weiyi.wx.order.admin;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.entity.InterfaceAccess;
import com.weiyi.wx.order.dao.request.GetInterfaceAccessRequest;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.QueryInterfaceAccessRequest;
import com.weiyi.wx.order.response.QueryInterfaceAccessResponse;
import com.weiyi.wx.order.service.api.InterfaceAccessService;
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
@RequestMapping("/interface/access")
public class InterfaceAccessController
{
    private Logger logger = LoggerFactory.getLogger(InterfaceAccessController.class);

    @Autowired
    private InterfaceAccessService interfaceAccessService;

    /*
    *接口访问统计
    */
    @RequestMapping(value = "/queryList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public QueryInterfaceAccessResponse queryList(@RequestBody QueryInterfaceAccessRequest request)
    {
        QueryInterfaceAccessResponse response = new QueryInterfaceAccessResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryList() func ");
        }

        //填充查询数据
        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }

        GetInterfaceAccessRequest getInterfaceAccessRequest = new GetInterfaceAccessRequest();

        CopyProperties.copy(getInterfaceAccessRequest,request);
        getInterfaceAccessRequest.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        
        //查询数量
        int total = interfaceAccessService.queryListCount(getInterfaceAccessRequest);
        response.setCount(total);

        List<InterfaceAccess> interfaceAccesses = interfaceAccessService.queryList(getInterfaceAccessRequest);

        if (interfaceAccesses != null && interfaceAccesses.size() > 0)
        {
            response.setInterfaceAccesses(interfaceAccesses.toArray(new InterfaceAccess[interfaceAccesses.size()]));
        }

        return response;
    }
}
