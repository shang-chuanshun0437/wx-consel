package com.weiyi.wx.order.admin;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.Role;
import com.weiyi.wx.order.dao.request.GetRoleRequest;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.AddRoleRequest;
import com.weiyi.wx.order.request.DeleteRoleRequest;
import com.weiyi.wx.order.request.QueryRoleRequest;
import com.weiyi.wx.order.request.UpdateRoleRequest;
import com.weiyi.wx.order.response.AddRoleResponse;
import com.weiyi.wx.order.response.DeleteRoleResponse;
import com.weiyi.wx.order.response.QueryRoleResponse;
import com.weiyi.wx.order.response.UpdateRoleResponse;
import com.weiyi.wx.order.service.api.RoleService;
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
@RequestMapping("/role")
public class AdminRoleController
{
    private Logger logger = LoggerFactory.getLogger(AdminRoleController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/addRole",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public AddRoleResponse addRole(@RequestBody AddRoleRequest request)
    {
        AddRoleResponse response = new AddRoleResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addRole() func ,the request:{}",request);
        }

        Role role = new Role();

        role.setRoleName(request.getRoleName());
        role.setRoleDesc(request.getRoleDesc());
        role.setStatus(Constant.SUCCESS);
        role.setCreateTime(TimeUtil.getCurrentTime());

        roleService.addRole(role);
        return response;
    }

    @RequestMapping(value = "/queryList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public QueryRoleResponse queryRole(@RequestBody QueryRoleRequest request)
    {
        QueryRoleResponse response = new QueryRoleResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addRole() func ,the request:{}",request);
        }

        if(request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }

        GetRoleRequest getRoleRequest = new GetRoleRequest();

        getRoleRequest.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        getRoleRequest.setRoleName(request.getRoleName());
        getRoleRequest.setStatus(request.getStatus());

        int total = roleService.queryRoleCount(getRoleRequest);
        response.setCount(total);

        List<Role> roles = roleService.queryRole(getRoleRequest);
        if(roles != null && roles.size() > 0)
        {
            response.setRoles(roles.toArray(new Role[roles.size()]));
        }

        return response;
    }

    @RequestMapping(value = "/updateRole",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public UpdateRoleResponse updateRole(@RequestBody UpdateRoleRequest request)
    {
        UpdateRoleResponse response = new UpdateRoleResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateRole() func ,the request:{}",request);
        }

        Role role = new Role();

        CopyProperties.copy(role,request);

        roleService.updateRole(role);

        return response;
    }

    @RequestMapping(value = "/deleteRole",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public DeleteRoleResponse deleteRole(@RequestBody DeleteRoleRequest request)
    {
        DeleteRoleResponse response = new DeleteRoleResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteRole() func ,the request:{}",request);
        }

        if (request.getId() != null)
        {
            roleService.deleteRole(request.getId());
        }
        return response;
    }
}
