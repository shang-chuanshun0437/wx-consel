package com.weiyi.wx.order.request;

public class UpdateMenuRequest extends BaseRequest{
    //店铺编号
    private String storeId;

    //食物编号
    private Integer foodId;

    //食物名称
    private String foodName;

    //食物图片URL
    private String foodImg;

    private String imgName;

    //食物所属的类目：1 小炒系列 ;2 凉菜系列 ； 3  海鲜系列； 4 汤系列；5 酒水饮料；6 主食
    private Integer category;

    //原价
    private Double oldPrice;

    //现价
    private Double newPrice;

    //会员价
    private Double vipPrice;

    //是否推荐 1 不推荐； 2 推荐
    private Integer recommend;

    //味道：1 不辣； 2 微辣； 3 中辣；4 特辣
    private Integer taste;

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

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getTaste() {
        return taste;
    }

    public void setTaste(Integer taste) {
        this.taste = taste;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
