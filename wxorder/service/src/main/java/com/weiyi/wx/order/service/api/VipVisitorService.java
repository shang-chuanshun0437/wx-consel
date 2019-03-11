package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.VipVisitor;
import com.weiyi.wx.order.dao.request.GetAllVipConsumeRequest;
import com.weiyi.wx.order.dao.request.GetVipConsumeRequest;
import com.weiyi.wx.order.dao.request.GetVipVisitorListRequest;

import java.util.List;

public interface VipVisitorService
{
    void addVip(VipVisitor vipVisitor);

    void deleteVip(VipVisitor vipVisitor);

    void updateVip(VipVisitor vipVisitor);

    List<VipVisitor> queryVipList(GetVipVisitorListRequest request);

    VipVisitor queryVipByUserPhoneAndVipId(VipVisitor request);

    List<StoreOrder> queryAllVipConsume(GetAllVipConsumeRequest request);

    double queryAllVipConsumePercent(GetAllVipConsumeRequest request);

    int queryVipListCount(GetVipVisitorListRequest request);

    int queryVipConsumeCount(GetVipConsumeRequest request);

    List<StoreOrder> queryVipConsumeList(GetVipConsumeRequest request);

}
