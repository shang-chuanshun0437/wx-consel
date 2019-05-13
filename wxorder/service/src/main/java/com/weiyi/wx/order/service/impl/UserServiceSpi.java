package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.redis.RedisClient;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.dto.H5QueryUserDto;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.dao.mapper.UserMapper;
import com.weiyi.wx.order.dao.request.AdminGetAllUserRequest;
import com.weiyi.wx.order.dao.request.GetPeriodSalesRequest;
import com.weiyi.wx.order.dao.request.GetUserAllSalesRequest;
import com.weiyi.wx.order.dao.request.H5GetUserRequest;
import com.weiyi.wx.order.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceSpi implements UserService
{
    private Logger logger = LoggerFactory.getLogger(UserServiceSpi.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisClient redisClient;

    /*
    *商家接入
    **/
    public void addUser(User user)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter insert() func,phoneNum:{}", user.getUserPhone());
        }

        //如果商家已经接入，则直接抛异常
        User dbUser = queryUserByPhone(user.getUserPhone());
        WxOrderAssert.isTrue(dbUser == null, ErrorCode.USER_EXIST,"business already exist.");

        //构造商家信息
        user.setCreateTime(TimeUtil.getCurrentTime());
        user.setUpdateTime(TimeUtil.getCurrentTime());
        user.setPassword("123abcde");
        user.setShopCount(0);

        userMapper.addUser(user);
    }

    public void deleteUser(Long userPhone) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserAllSales() func,phoneNum:{}", userPhone);
        }
        userMapper.deleteByPhoneNum(userPhone);
    }

    public double queryUserAllSales(GetUserAllSalesRequest getUserAllSalesRequest) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserAllSales() func,phoneNum:{}", getUserAllSalesRequest.getUserPhone());
        }
        if (userMapper.queryUserAllSales(getUserAllSalesRequest) != null){
            return userMapper.queryUserAllSales(getUserAllSalesRequest);
        }
        return 0;
    }

    public List<StoreOrder> queryPeriodSales(GetPeriodSalesRequest getPeriodSalesRequest) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryPeriodSales() func,phoneNum:{}", getPeriodSalesRequest.getUserPhone());
        }
        return userMapper.queryPeriodSales(getPeriodSalesRequest);
    }

    @Transactional
    public void updateUser(User user) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateUser() func,phoneNum:{}", user.getUserPhone());
        }

        userMapper.updateUser(user);
    }

    public User queryUserByPhone(Long userPhone) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryUserByPhone() func,phoneNum:{}", userPhone);
        }
        User user = userMapper.queryUserByPhone(userPhone);
        return user;
    }

    public H5QueryUserDto h5QueryUser(H5GetUserRequest request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter h5QueryUser() func,request:{}", request);
        }
        return userMapper.h5QueryUser(request);
    }

    public void updatePassword(User user) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updatePassword() func,request:{}", user.getUserPhone());
        }
        userMapper.updateUser(user);
    }

    @Override
    public List<User> adminQueryAllUser(AdminGetAllUserRequest adminGetAllUserRequest) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter adminQueryAllUser() func,request:{}", adminGetAllUserRequest.getUserPhone());
        }
        return userMapper.adminQueryAllUser(adminGetAllUserRequest);
    }

    @Override
    public int adminQueryAllUserCount(AdminGetAllUserRequest adminGetAllUserRequest) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter adminQueryAllUserCount() func,request:{}", adminGetAllUserRequest.getUserPhone());
        }
        return userMapper.adminQueryAllUserCount(adminGetAllUserRequest);
    }

}
