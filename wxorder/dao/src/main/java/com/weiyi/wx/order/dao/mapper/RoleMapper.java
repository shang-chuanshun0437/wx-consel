package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.Role;
import com.weiyi.wx.order.dao.request.GetRoleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleMapper
{
    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(int id);

    List<Role> queryRole(GetRoleRequest request);

    int queryRoleCount(GetRoleRequest request);
}
