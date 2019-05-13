package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.utils.FileFactory;
import com.weiyi.wx.order.common.utils.TimeUtil;
import com.weiyi.wx.order.dao.entity.Menu;
import com.weiyi.wx.order.dao.entity.PaySetting;
import com.weiyi.wx.order.dao.entity.Store;
import com.weiyi.wx.order.dao.entity.StoreTable;
import com.weiyi.wx.order.dao.mapper.MenuMapper;
import com.weiyi.wx.order.dao.mapper.PaySettingMapper;
import com.weiyi.wx.order.dao.mapper.StoreMapper;
import com.weiyi.wx.order.dao.mapper.StoreTableMapper;
import com.weiyi.wx.order.dao.request.GetMenuRequest;
import com.weiyi.wx.order.dao.request.H5GetMenuRequest;
import com.weiyi.wx.order.service.api.MenuService;
import com.weiyi.wx.order.service.api.PaySettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaySettingServiceSpi implements PaySettingService
{
    private Logger logger = LoggerFactory.getLogger(PaySettingServiceSpi.class);

    @Autowired
    private PaySettingMapper paySettingMapper;

    public void addPaySetting(PaySetting paySetting) {
        if (logger.isDebugEnabled()){
            logger.debug("inter addPaySetting() func.the user is:{}",paySetting.getUserPhone());
        }
        paySettingMapper.addPaySetting(paySetting);
    }

    @Override
    public PaySetting queryPaySetting(Long userPhone) {
        if (logger.isDebugEnabled()){
            logger.debug("inter queryPaySetting() func.the user is:{}",userPhone);
        }
        return paySettingMapper.queryPaySetting(userPhone);
    }

    @Override
    public void updatePaySetting(PaySetting paySetting) {
        if (logger.isDebugEnabled()){
            logger.debug("inter updatePaySetting() func.the pay setting is:{}",paySetting);
        }
        //查询该用户是否存在支付设置
        PaySetting dbPaySetting = paySettingMapper.queryPaySetting(paySetting.getUserPhone());
        if (dbPaySetting == null){
            paySetting.setCreateTime(TimeUtil.getCurrentTime());
            paySettingMapper.addPaySetting(paySetting);
        }else {
            paySettingMapper.updatePaySetting(paySetting);
        }
    }
}
