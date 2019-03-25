package com.weiyi.wx.order.response;

import com.weiyi.wx.order.service.domain.WXPayParameter;

public class H5AddStoreOrderResponse extends BaseResponse
{
    //如果扫码的时候采用的是在线支付----微信支付，此参数必须返回给前端
    private WXPayParameter wxPayParameter;

    public WXPayParameter getWxPayParameter() {
        return wxPayParameter;
    }

    public void setWxPayParameter(WXPayParameter wxPayParameter) {
        this.wxPayParameter = wxPayParameter;
    }
}
