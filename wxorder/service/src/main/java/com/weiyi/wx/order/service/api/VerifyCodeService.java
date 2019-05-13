package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.VerifyCode;
import com.weiyi.wx.order.dao.request.QueryVerifyCodeListReq;

import java.util.List;

public interface VerifyCodeService
{
    void addCode(VerifyCode verifyCode);

    int queryVerifyCodeInfoCount(QueryVerifyCodeListReq request);

    List<VerifyCode> queryVerifyCodeInfo(QueryVerifyCodeListReq request);
}
