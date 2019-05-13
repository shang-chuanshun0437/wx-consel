package com.weiyi.wx.order.admin;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.dao.request.AdminGetAllUserRequest;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.AdminAddUserRequest;
import com.weiyi.wx.order.request.AdminDeleteUserRequest;
import com.weiyi.wx.order.request.AdminQueryUserRequest;
import com.weiyi.wx.order.request.AdminUpdateUserRequest;
import com.weiyi.wx.order.response.AddUserResponse;
import com.weiyi.wx.order.response.AdminDeleteUserResponse;
import com.weiyi.wx.order.response.AdminQueryUserResponse;
import com.weiyi.wx.order.response.AdminUpdateUserResponse;
import com.weiyi.wx.order.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
* 管理员----商家
*/
@Controller
@RequestMapping("/admin/user")
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
    public AdminQueryUserResponse queryAllUser(@RequestBody AdminQueryUserRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryAllUser() func,phone num:{}",request.getUserPhone());
        }
        AdminQueryUserResponse response = new AdminQueryUserResponse();
        Result result = new Result();
        response.setResult(result);

        AdminGetAllUserRequest adminGetAllUserRequest = new AdminGetAllUserRequest();

        if (request.getCurrentPage() != null){
            request.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        }
        adminGetAllUserRequest.setBeginTime(request.getBeginTime());
        adminGetAllUserRequest.setCurrentPage(request.getCurrentPage());
        adminGetAllUserRequest.setEndTime(request.getEndTime());
        adminGetAllUserRequest.setUserPhone(request.getBusinessPhone());

        int total = userService.adminQueryAllUserCount(adminGetAllUserRequest);
        response.setTotal(total);

        if (total > 0){
            List<User> users = userService.adminQueryAllUser(adminGetAllUserRequest);

            if (users != null && users.size() > 0){
                response.setUsers(users.toArray(new User[users.size()]));
            }
        }

        return response;
    }

    /*
     * 后台管理人员新增商家
     */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public AddUserResponse addUser(@RequestBody AdminAddUserRequest request)
    {
        //由于商家接入特别重要，故输出info日志，便于后续查看
        logger.info("inter addUser() func.the manager is:{},the business is:{},the business shop total is:{}",
                request.getUserPhone(),request.getNeedAddPhone(),request.getShopTotal());

        AddUserResponse response = new AddUserResponse();
        Result result = new Result();
        response.setResult(result);

        User user = new User();
        user.setUserPhone(request.getNeedAddPhone());
        user.setShopTotal(request.getShopTotal());

        try {
            userService.addUser(user);
        }catch (WxOrderException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }

        return response;
    }

    /*
     * 后台管理人员删除商家
     */
    @RequestMapping(value = "/delete",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public AdminDeleteUserResponse deleteUser(@RequestBody AdminDeleteUserRequest request)
    {
        //由于商家接入特别重要，故输出info日志，便于后续查看
        logger.info("inter deleteUser() func.the manager is:{},the business is:{}",
                request.getUserPhone(),request.getNeedDeletePhone());

        AdminDeleteUserResponse response = new AdminDeleteUserResponse();
        Result result = new Result();
        response.setResult(result);

        try {
            userService.deleteUser(request.getNeedDeletePhone());
        }catch (WxOrderException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }

        return response;
    }

    /*
     * 后台管理人员更新商家
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public AdminUpdateUserResponse updateUser(@RequestBody AdminUpdateUserRequest request)
    {
        //由于商家接入特别重要，故输出info日志，便于后续查看
        logger.info("inter updateUser() func.the manager is:{},the business is:{}",
                request.getUserPhone(),request.getNeedUpdatePhone());

        AdminUpdateUserResponse response = new AdminUpdateUserResponse();
        Result result = new Result();
        response.setResult(result);

        User user = new User();
        user.setUserPhone(request.getNeedUpdatePhone());
        user.setShopTotal(request.getShopTotal());
        try {
            userService.updateUser(user);
        }catch (WxOrderException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }

        return response;
    }
}
