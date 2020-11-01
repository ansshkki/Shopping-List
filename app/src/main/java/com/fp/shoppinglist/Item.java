package com.fp.shoppinglist;

public class Item {

    private String name;
    private String shopName;
    private String quantity;
    private String status;
    private String details;
    private int statusPhoto;

    public Item() {
    }

    public Item(String name, String shopName, String quantity, String status, int statusPhoto, String details) {
        this.name = name;
        this.status = status;
        this.shopName = shopName;
        this.details = details;
        this.quantity = quantity;
        this.statusPhoto = statusPhoto;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShopName() {
        return shopName;
    }

    public String getDetails() {
        return details;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getStatusPhoto() {
        return statusPhoto;
    }

    public void setStatusPhoto(int statusPhoto) {
        this.statusPhoto = statusPhoto;
    }

}
