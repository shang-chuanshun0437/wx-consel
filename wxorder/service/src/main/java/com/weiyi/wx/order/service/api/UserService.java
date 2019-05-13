package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.dto.H5QueryUserDto;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.dao.request.AdminGetAllUserRequest;
import com.weiyi.wx.order.dao.request.GetPeriodSalesRequest;
import com.weiyi.wx.order.dao.request.GetUserAllSalesRequest;
import com.weiyi.wx.order.dao.request.H5GetUserRequest;

import java.util.List;

public interface UserService
{
    void addUser(User user);

    void deleteUser(Long userPhone);

    void updateUser(User user);

    double queryUserAllSales(GetUserAllSalesRequest getUserAllSalesRequest);

    List<StoreOrder> queryPeriodSales(GetPeriodSalesRequest getPeriodSalesRequest);

    User queryUserByPhone(Long userPhone);

    H5QueryUserDto h5QueryUser(H5GetUserRequest request);

    void updatePassword(User user);

    List<User> adminQueryAllUser(AdminGetAllUserRequest adminGetAllUserRequest);

    int adminQueryAllUserCount(AdminGetAllUserRequest adminGetAllUserRequest);
}
