package com.weiyi.wx.order.response;

import com.weiyi.wx.order.dao.entity.VerifyCode;

public class QueryVerifyCodeResponse extends BaseResponse
{
    private int count;

    private VerifyCode[] verifyCodes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public VerifyCode[] getVerifyCodes() {
        return verifyCodes;
    }

    public void setVerifyCodes(VerifyCode[] verifyCodes) {
        this.verifyCodes = verifyCodes;
    }
}
