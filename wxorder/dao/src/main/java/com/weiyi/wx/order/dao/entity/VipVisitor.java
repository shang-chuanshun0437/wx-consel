package com.weiyi.wx.order.dao.entity;

/*
*会员
*/
public class VipVisitor
{
    private Integer id;

    private String vipId;

    private Long userPhone;

    //实际充值金额
    private Double realAmount;

    //消费次数
    private int consumCount;

    //最近一次消费时间
    private String updateTime;

    //有效期
    private String validTime;

    private String createTime;

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public int getConsumCount() {
        return consumCount;
    }

    public void setConsumCount(int consumCount) {
        this.consumCount = consumCount;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    @Override
    public String toString() {
        return "VipVisitor{" +
                "id=" + id +
                ", vipId='" + vipId + '\'' +
                ", userPhone=" + userPhone +
                ", realAmount=" + realAmount +
                ", consumCount=" + consumCount +
                ", updateTime='" + updateTime + '\'' +
                ", validTime='" + validTime + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
