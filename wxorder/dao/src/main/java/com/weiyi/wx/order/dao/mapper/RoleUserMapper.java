package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.RoleUser;
import com.weiyi.wx.order.dao.request.GetRoleUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleUserMapper
{
    void addRole(RoleUser roleUser);

    void deleteRoleUserByPhone(Long userPhone);

    RoleUser queryRoleByPhone(Long userPhone);

    List<RoleUser> queryRole(GetRoleUserRequest request);

    int queryRoleCount(GetRoleUserRequest request);

    String queryUserRoleByPhone(Long userPhone);
}
