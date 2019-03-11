package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.dto.H5QueryUserDto;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.VipVisitor;
import com.weiyi.wx.order.dao.request.GetAllVipConsumeRequest;
import com.weiyi.wx.order.dao.request.GetVipConsumeRequest;
import com.weiyi.wx.order.dao.request.GetVipVisitorListRequest;
import com.weiyi.wx.order.dao.request.H5GetUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VipVisitorMapper
{
    void addVip(VipVisitor vipVisitor);

    void deleteVip(VipVisitor vipVisitor);

    void deleteVipById(int vipId);

    VipVisitor queryVip(VipVisitor vipVisitor);

    VipVisitor queryVipById(int vipId);

    List<VipVisitor> queryVipList(GetVipVisitorListRequest vipVisitor);

    List<StoreOrder> queryAllVipConsume(GetAllVipConsumeRequest request);

    Double queryAllVipConsumeMoney(GetAllVipConsumeRequest request);

    Double queryAllMoney(GetAllVipConsumeRequest request);

    int queryVipListCount(GetVipVisitorListRequest vipVisitor);

    int queryVipConsumeCount(GetVipConsumeRequest request);

    List<StoreOrder> queryVipConsumeList(GetVipConsumeRequest request);

}
