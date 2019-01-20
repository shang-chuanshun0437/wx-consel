package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.RoleUser;
import com.weiyi.wx.order.dao.request.GetRoleUserRequest;

import java.util.List;

public interface RoleUserService
{
    void addRole(RoleUser roleUser);

    void deleteRoleUserByPhone(Long userPhone);

    RoleUser queryRoleByPhone(Long userPhone);

    String queryUserRoleByPhone(Long userPhone);

    List<RoleUser> queryRole(GetRoleUserRequest request);

    int queryRoleCount(GetRoleUserRequest request);
}
