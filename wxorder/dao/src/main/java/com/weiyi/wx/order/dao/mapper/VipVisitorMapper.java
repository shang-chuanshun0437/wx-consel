package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.Menu;
import com.weiyi.wx.order.dao.entity.VipVisitor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VipVisitorMapper
{
    void addVip(VipVisitor vipVisitor);

    void deleteVip(int vipId);

    VipVisitor queryVip(int vipId);

    List<VipVisitor> queryVipList(VipVisitor vipVisitor);

    int queryVipListCount(VipVisitor vipVisitor);
}
