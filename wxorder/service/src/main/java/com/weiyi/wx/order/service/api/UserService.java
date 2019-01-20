package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.User;

public interface UserService
{
    void addUser(User user);

    void deleteUser(Long userPhone);

    void updateUser(User user);

    User queryUserByPhone(Long userPhone);
}
