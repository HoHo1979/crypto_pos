package com.iotarch.cryptopos.entity;

public class Item {

    String itemName;

    Double itemPrice;

    Double itemQuantity;

    public Item() {
    }

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public Item(String itemName, Double itemPrice, Double itemQuantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
