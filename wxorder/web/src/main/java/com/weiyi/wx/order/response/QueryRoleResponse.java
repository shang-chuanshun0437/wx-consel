package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.Role;

public class QueryRoleResponse extends BaseResponse
{
    private int count;

    private Role[] roles;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }
}
