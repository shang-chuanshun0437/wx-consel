package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.dto.H5QueryUserDto;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.dao.request.AdminGetAllUserRequest;
import com.weiyi.wx.order.dao.request.GetPeriodSalesRequest;
import com.weiyi.wx.order.dao.request.GetUserAllSalesRequest;
import com.weiyi.wx.order.dao.request.H5GetUserRequest;
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

    H5QueryUserDto h5QueryUser(H5GetUserRequest request);

    Double queryUserAllSales(GetUserAllSalesRequest getUserAllSalesRequest);

    List<StoreOrder> queryPeriodSales(GetPeriodSalesRequest getPeriodSalesRequest);

    List<User> adminQueryAllUser(AdminGetAllUserRequest adminGetAllUserRequest);

    int adminQueryAllUserCount(AdminGetAllUserRequest adminGetAllUserRequest);
}
