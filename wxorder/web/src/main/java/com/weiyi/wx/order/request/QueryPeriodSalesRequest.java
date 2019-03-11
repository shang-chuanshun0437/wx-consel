package com.weiyi.wx.order.request;

public class QueryPeriodSalesRequest extends BaseRequest{
    //1 按月统计；2 按天统计
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
