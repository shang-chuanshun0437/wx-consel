package com.weiyi.wx.order.H5Controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.dao.entity.VipVisitor;
import com.weiyi.wx.order.request.H5QueryVipVisitorListRequest;
import com.weiyi.wx.order.response.H5QueryVipVisitorListResponse;
import com.weiyi.wx.order.service.api.UserService;
import com.weiyi.wx.order.service.api.VipVisitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/h5/store/vip")
public class H5VipVisitorController
{
    private Logger logger = LoggerFactory.getLogger(H5VipVisitorController.class);

    @Autowired
    private VipVisitorService vipVisitorService;

    @Autowired
    private UserService userService;
    /*
     *查询会员
     */
    @RequestMapping(value = "/query",method = {RequestMethod.POST})
    @ResponseBody
    public H5QueryVipVisitorListResponse h5QueryVip(@RequestBody H5QueryVipVisitorListRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter h5QueryVip() func,user num:{}",request.getUserPhone());
        }

        H5QueryVipVisitorListResponse response = new H5QueryVipVisitorListResponse();
        Result result = new Result();
        response.setResult(result);

        VipVisitor vipVisitor = new VipVisitor();
        CopyProperties.copy(vipVisitor,request);

        VipVisitor dbVipVisitor = vipVisitorService.queryVipByUserPhoneAndVipId(vipVisitor);
        if (dbVipVisitor == null){
            result.setRetCode(ErrorCode.VIP_NOT_EXIST);
            result.setRetMsg("vip not exist");
        }

        return response;
    }
}
