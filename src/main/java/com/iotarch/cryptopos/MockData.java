package com.iotarch.cryptopos;

import com.iotarch.cryptopos.entity.Category;
import com.iotarch.cryptopos.entity.Item;

import java.util.*;

public class MockData {

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    List<Category> categoryList = new ArrayList<>();
    String categoryName [] = {"Coffee","Cake","Food","Tea","Fruit","Meat"};
    String coffeeName[] ={"Latte","Long Black","Cappuccino","Espresso","Piccolo","Mocha","Flat White"};


    public MockData() {

       List<String> coffeNameList=Arrays.asList(coffeeName);
       coffeNameList.sort(Comparator.naturalOrder());

       List<String> categoryNameList = Arrays.asList(categoryName);
       categoryNameList.sort(Comparator.naturalOrder());


        for(String name:categoryNameList) {

            Category category = new Category();
            category.setCategoryName(name);

            switch (name) {
                case "Coffee":
                    List<Item> itemsList = new ArrayList<>();

                    for(String cname:coffeNameList){
                        itemsList.add(new Item(cname,1.0,1.0));
                    }

                    category.setItemList(itemsList);
                    break;

            }

            categoryList.add(category);
        }


    }
}
