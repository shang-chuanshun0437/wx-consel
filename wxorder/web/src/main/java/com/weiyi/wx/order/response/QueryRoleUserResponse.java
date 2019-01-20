package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.RoleUser;

public class QueryRoleUserResponse extends BaseResponse
{
    private int count;

    private RoleUser[] roleUsers;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public RoleUser[] getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(RoleUser[] roleUsers) {
        this.roleUsers = roleUsers;
    }
}
