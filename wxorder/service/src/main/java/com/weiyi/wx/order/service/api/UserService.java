package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.dto.H5QueryUserDto;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.User;
import com.weiyi.wx.order.dao.request.GetPeriodSalesRequest;
import com.weiyi.wx.order.dao.request.GetUserAllSalesRequest;
import com.weiyi.wx.order.dao.request.H5GetUserRequest;

import java.util.List;

public interface UserService
{
    void addUser(User user);

    void deleteUser(Long userPhone);

    double queryUserAllSales(GetUserAllSalesRequest getUserAllSalesRequest);

    List<StoreOrder> queryPeriodSales(GetPeriodSalesRequest getPeriodSalesRequest);

    void updateUser(User user);

    User queryUserByPhone(Long userPhone);

    H5QueryUserDto h5QueryUser(H5GetUserRequest request);

    void updatePassword(User user);
}
