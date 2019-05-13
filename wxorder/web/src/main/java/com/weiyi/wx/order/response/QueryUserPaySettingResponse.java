package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.PaySetting;

public class QueryUserPaySettingResponse extends BaseResponse
{
    private PaySetting paySetting;

    public PaySetting getPaySetting() {
        return paySetting;
    }

    public void setPaySetting(PaySetting paySetting) {
        this.paySetting = paySetting;
    }
}
