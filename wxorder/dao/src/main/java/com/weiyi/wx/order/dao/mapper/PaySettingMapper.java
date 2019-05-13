package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.PaySetting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaySettingMapper
{
    void addPaySetting(PaySetting paySetting);

    PaySetting queryPaySetting(Long userPhone);

    void updatePaySetting(PaySetting paySetting);
}
