package com.weiyi.wx.order.request;

public class QueryMenuByIdRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    //食物编号
    private Integer foodId;

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

}
