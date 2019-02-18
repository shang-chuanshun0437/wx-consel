package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.VipVisitor;
import com.weiyi.wx.order.dao.request.GetVipConsumeRequest;
import com.weiyi.wx.order.dao.request.GetVipVisitorListRequest;
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

    int queryVipListCount(GetVipVisitorListRequest vipVisitor);

    int queryVipConsumeCount(GetVipConsumeRequest request);

    List<StoreOrder> queryVipConsumeList(GetVipConsumeRequest request);
}
