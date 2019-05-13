package com.weiyi.wx.order.service.impl;

import com.weiyi.wx.order.dao.entity.VerifyCode;
import com.weiyi.wx.order.dao.mapper.VerifyCodeMapper;
import com.weiyi.wx.order.dao.request.QueryVerifyCodeListReq;
import com.weiyi.wx.order.service.api.VerifyCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyCodeServiceSpi implements VerifyCodeService
{
    private Logger logger = LoggerFactory.getLogger(VerifyCodeServiceSpi.class);

    @Autowired
    private VerifyCodeMapper verifyCodeMapper;

    public void addCode(VerifyCode verifyCode) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addCode() func,user phone:{}",verifyCode.getUserPhone());
        }
        verifyCodeMapper.addCode(verifyCode);
    }

    public int queryVerifyCodeInfoCount(QueryVerifyCodeListReq request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryVerifyCodeInfoCount() func,user phone:{}",request.getUserPhone());
        }

        return verifyCodeMapper.queryVerifyCodeInfoCount(request);
    }

    public List<VerifyCode> queryVerifyCodeInfo(QueryVerifyCodeListReq request) {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryVerifyCodeInfoCount() func,user phone:{}",request.getUserPhone());
        }

        return verifyCodeMapper.queryVerifyCodeInfo(request);
    }
}
