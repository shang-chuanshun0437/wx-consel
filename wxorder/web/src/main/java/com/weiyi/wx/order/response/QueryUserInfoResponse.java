package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.User;

public class QueryUserInfoResponse extends BaseResponse
{
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
