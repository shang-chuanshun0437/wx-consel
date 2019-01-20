package com.weiyi.wx.order.request;

public class QueryMenuRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    //食物编号
    private Integer foodId;

    //食物名称
    private String foodName;

    //食物所属的类目：1 小炒系列 ;2 凉菜系列 ； 3  海鲜系列； 4 汤系列；5 酒水饮料；6 主食
    private Integer category;

    //是否推荐 1 不推荐； 2 推荐
    private Integer recommend;

    private Integer currentPage;

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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
