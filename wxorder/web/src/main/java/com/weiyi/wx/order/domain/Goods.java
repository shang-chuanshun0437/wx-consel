package com.weiyi.wx.order.domain;

import com.weiyi.wx.order.dao.entity.Menu;

public class Goods {
    private String categoryName;

    private Menu[] foods;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Menu[] getFoods() {
        return foods;
    }

    public void setFoods(Menu[] foods) {
        this.foods = foods;
    }
}
