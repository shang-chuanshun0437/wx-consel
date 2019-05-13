package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.VerifyCode;
import com.weiyi.wx.order.dao.request.QueryVerifyCodeListReq;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface VerifyCodeMapper
{
    void addCode(VerifyCode verifyCode);

    int queryVerifyCodeInfoCount(QueryVerifyCodeListReq request);

    List<VerifyCode> queryVerifyCodeInfo(QueryVerifyCodeListReq request);
}
