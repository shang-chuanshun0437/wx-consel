package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserMapper
{
    void addUser(User user);

    int countByPhone(Long userPhone);

    User queryUserByPhone(Long userPhone);

    void updateUser(User user);

    void deleteByPhoneNum(Long userPhone);
}
