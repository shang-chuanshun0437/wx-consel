package com.weiyi.wx.order.request;

public class AdminQueryUserRequest extends BaseRequest{
    private Long businessPhone;

    private Integer currentPage;

    private String beginTime;

    private String endTime;

    public Long getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(Long businessPhone) {
        this.businessPhone = businessPhone;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
