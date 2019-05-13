package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.Role;
import com.weiyi.wx.order.dao.request.GetRoleRequest;

import java.util.List;

public interface RoleService
{
    void addRole(Role role);

    List<Role> queryRole(GetRoleRequest request);

    int queryRoleCount(GetRoleRequest request);

    void updateRole(Role role);

    void deleteRole(int id);
}
