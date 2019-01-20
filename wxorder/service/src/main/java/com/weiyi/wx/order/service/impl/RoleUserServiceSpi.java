package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.dao.entity.RoleUser;
import com.weiyi.wx.order.dao.mapper.RoleUserMapper;
import com.weiyi.wx.order.dao.request.GetRoleUserRequest;
import com.weiyi.wx.order.service.api.RoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleUserServiceSpi implements RoleUserService
{
    private Logger logger = LoggerFactory.getLogger(RoleUserServiceSpi.class);

    @Autowired
    private RoleUserMapper roleUserMapper;

    public void addRole(RoleUser roleUser)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter roleUser addRole() func,user phone:{}", roleUser.getUserPhone());
        }

        //首先判断添加的用户是已经存在,存在则报错
        RoleUser dbRole = queryRoleByPhone(roleUser.getUserPhone());
        WxOrderAssert.isTrue(dbRole == null || dbRole.getUserPhone() == null, ErrorCode.ROLE_USER_EXIST,"role user exist.");

        roleUserMapper.addRole(roleUser);
    }

    public void deleteRoleUserByPhone(Long userPhone) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter roleUser deleteRoleUserByPhone() func,user phone:{}", userPhone);
        }

        roleUserMapper.deleteRoleUserByPhone(userPhone);
    }

    public RoleUser queryRoleByPhone(Long userPhone)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter roleUser addRole() func,user phone:{}",userPhone);
        }

        return roleUserMapper.queryRoleByPhone(userPhone);
    }

    public String queryUserRoleByPhone(Long userPhone) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter role queryUserRoleByPhone() func,user phone:{}",userPhone);
        }
        return roleUserMapper.queryUserRoleByPhone(userPhone);
    }

    public List<RoleUser> queryRole(GetRoleUserRequest request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter role queryRoleCount() func,user phone:{}",request);
        }
        return roleUserMapper.queryRole(request);
    }

    public int queryRoleCount(GetRoleUserRequest request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter role queryRoleCount() func,user phone:{}",request);
        }
        return roleUserMapper.queryRoleCount(request);
    }
}
