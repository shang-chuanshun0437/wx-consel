package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.User;

public class AdminQueryUserResponse extends BaseResponse
{
    private User[] users;

    private int total;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
