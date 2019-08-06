package com.desired.offermachi.customer.model;

public class smart_shopping_model {  int img;
    String productname,unfolltext;


    public smart_shopping_model(int img, String productname) {
        this.img = img;
        this.productname = productname;

    }
    public void setImg(int img) {
        this.img = img;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }


    public int getImg() {
        return img;
    }

    public String getProductname() {
        return productname;
    }


}
