package com.weiyi.wx.order.admin;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.AddUserRequest;
import com.weiyi.wx.order.response.AddUserResponse;
import com.weiyi.wx.order.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* 管理商家
*/
@Controller
@RequestMapping("/manage/user")
public class AdminUserController
{
    private Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private UserService userService;
    /*
     * 后台管理人员获取接入的商家
     */
    @RequestMapping(value = "/query/allUser",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public void queryAllUser()
    {

    }

    /*
     * 后台管理人员新增商家
     */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public AddUserResponse addUser(@RequestBody AddUserRequest request)
    {
        //由于商家接入特别重要，故输出error日志，便于后续查看
        logger.error("inter addUser() func.the manager is:{},the business is:{},the business shop total is:{}",
                request.getUserPhone(),request.getNeedAddPhone(),request.getShopTotal());

        AddUserResponse response = new AddUserResponse();
        Result result = new Result();
        response.setResult(result);

        User user = new User();
        user.setUserPhone(request.getNeedAddPhone());
        user.getShopTotal();

        try {
            userService.addUser(user);
        }catch (WxOrderException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }

        return response;
    }
}
