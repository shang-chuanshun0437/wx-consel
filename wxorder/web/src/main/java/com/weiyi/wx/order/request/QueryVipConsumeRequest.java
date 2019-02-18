package com.weiyi.wx.order.request;

public class QueryVipConsumeRequest extends BaseRequest{
    //会员编号
    private String vipId;

    //查询第几页
    private Integer currentPage;

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
