package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.redis.RedisClient;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.entity.VipVisitor;
import com.weiyi.wx.order.dao.mapper.VipVisitorMapper;
import com.weiyi.wx.order.dao.request.GetAllVipConsumeRequest;
import com.weiyi.wx.order.dao.request.GetVipConsumeRequest;
import com.weiyi.wx.order.dao.request.GetVipVisitorListRequest;
import com.weiyi.wx.order.service.api.VipVisitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VipVisitorServiceSpi implements VipVisitorService
{
    private Logger logger = LoggerFactory.getLogger(VipVisitorServiceSpi.class);

    @Autowired
    private VipVisitorMapper vipVisitorMapper;

    public void addVip(VipVisitor vipVisitor) {
        if (logger.isDebugEnabled()){
            logger.debug("inter addVip() func.the vipVisitor is:{}",vipVisitor);
        }

        //校验VIP是否存在
        VipVisitor dbVipVisitor = vipVisitorMapper.queryVip(vipVisitor);
        WxOrderAssert.isTrue(dbVipVisitor == null, ErrorCode.VIP_EXIST,"vip already exist.");

        vipVisitor.setCreateTime(TimeUtil.getCurrentTime());
        vipVisitor.setConsumCount(0);
        vipVisitor.setUpdateTime(TimeUtil.getCurrentTime());

        vipVisitorMapper.addVip(vipVisitor);
    }

    public void deleteVip(VipVisitor vipVisitor) {
        if (logger.isDebugEnabled()){
            logger.debug("inter deleteVip() func.the vipVisitor is:{}",vipVisitor);
        }
        vipVisitorMapper.deleteVip(vipVisitor);
    }

    @Transactional
    public void updateVip(VipVisitor vipVisitor) {
        if (logger.isDebugEnabled()){
            logger.debug("inter updateVip() func.the vipVisitor is:{}",vipVisitor);
        }
        //去数据库中查询id是否存在
        VipVisitor dbVipVisitor = vipVisitorMapper.queryVipById(vipVisitor.getId());
        WxOrderAssert.isTrue(dbVipVisitor != null,ErrorCode.VIP_NOT_EXIST,"vip not exit.");

        //先删除再插入
        vipVisitorMapper.deleteVipById(vipVisitor.getId());

        vipVisitor.setConsumCount(dbVipVisitor.getConsumCount());
        vipVisitor.setUpdateTime(dbVipVisitor.getUpdateTime());
        vipVisitor.setCreateTime(dbVipVisitor.getCreateTime());
        vipVisitorMapper.addVip(vipVisitor);
    }

    public List<VipVisitor> queryVipList(GetVipVisitorListRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryVipList() func.the user phone is:{}",request.getUserPhone());
        }
        return vipVisitorMapper.queryVipList(request);
    }

    public VipVisitor queryVipByUserPhoneAndVipId(VipVisitor request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryVipByUserPhoneAndVipId() func.the user phone is:{}",request.getUserPhone());
        }
        return vipVisitorMapper.queryVip(request);
    }

    public List<StoreOrder> queryAllVipConsume(GetAllVipConsumeRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryVipList() func.the user phone is:{}",request.getUserPhone());
        }

        return vipVisitorMapper.queryAllVipConsume(request);
    }

    public double queryAllVipConsumePercent(GetAllVipConsumeRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryVipList() func.the user phone is:{}",request.getUserPhone());
        }

        Double vipMoney = vipVisitorMapper.queryAllVipConsumeMoney(request);
        Double allMoney = vipVisitorMapper.queryAllMoney(request);

        if (vipMoney != null && allMoney != null){
            return vipMoney.doubleValue() / allMoney.doubleValue();
        }

        return 0.0;
    }

    public int queryVipListCount(GetVipVisitorListRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryVipListCount() func.the user phone is:{}",request.getUserPhone());
        }
        return vipVisitorMapper.queryVipListCount(request);
    }

    public int queryVipConsumeCount(GetVipConsumeRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryVipConsumeCount() func.the user phone is:{}",request.getUserPhone());
        }
        return vipVisitorMapper.queryVipConsumeCount(request);
    }

    public List<StoreOrder> queryVipConsumeList(GetVipConsumeRequest request) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryVipConsumeList() func.the user phone is:{}",request.getUserPhone());
        }
        return vipVisitorMapper.queryVipConsumeList(request);
    }
}
