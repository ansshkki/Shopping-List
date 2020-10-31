package com.fp.shoppinglist;

public class Item {

    private String name;
    private String shopName;
    private String quantity;
    private String status;
    private String details;
    private String statusPhoto ;


    public Item(){

    }


    public Item(String name, String shopName, String quantity, String status,String statusPhoto, String details) {
        this.name = name;
        this.status = status;
        this.shopName = shopName;
        this.details = details;
        this.quantity = quantity;
        this.statusPhoto = statusPhoto ;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
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

    public String getStatusPhoto(){return statusPhoto;}

    public void setStatus(String status){
        this.status = status ;
    }

    public void setStatusPhoto(String statusPhoto){
        this.statusPhoto = statusPhoto ;
    }


}
