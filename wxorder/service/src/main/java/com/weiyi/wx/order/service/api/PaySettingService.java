package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.PaySetting;

import java.util.List;

public interface PaySettingService
{
    void addPaySetting(PaySetting paySetting);

    PaySetting queryPaySetting(Long userPhone);

    void updatePaySetting(PaySetting paySetting);
}
