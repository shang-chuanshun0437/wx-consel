package com.weiyi.wx.order.dao.request;

import com.weiyi.wx.order.dao.entity.StoreOrder;

public class GetVipConsumeRequest extends StoreOrder
{
    private Integer currentPage;

    private String vipId;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }
}
