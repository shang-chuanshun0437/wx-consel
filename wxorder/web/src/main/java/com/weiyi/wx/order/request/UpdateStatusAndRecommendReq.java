package com.weiyi.wx.order.request;

public class UpdateStatusAndRecommendReq extends BaseRequest{
    //店铺编号
    private String storeId;

    //食物编号
    private Integer foodId;

    //是否推荐 1 不推荐； 2 推荐
    private Integer recommend;

    //是否售罄
    private Integer status;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
