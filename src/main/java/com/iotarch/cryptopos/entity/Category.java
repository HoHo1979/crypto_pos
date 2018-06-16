package com.iotarch.cryptopos.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {

    String categoryName;

    //@OneToMany
    List<Item> itemList = new ArrayList<>();

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
