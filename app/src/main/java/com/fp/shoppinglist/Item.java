package com.fp.shoppinglist;

public class Item {

    private String name;
    private String shopName;
    private String quantity;
    private String status;
    private String details ;



    public Item ( String name, String shopName, String quantity, String status, String details ){
        this.name= name ;
        this.status = status ;
        this.shopName = shopName;
        this.details = details ;
        this.quantity = quantity ;


    }





    public String getName(){
        return name ;
    }

    public String getStatus(){
        return status ;
    }
    public String getShopName(){
        return shopName ;
    }
    public String getDetails(){
        return details ;
    }
    public String getQuantity(){
        return quantity ;
    }

}
