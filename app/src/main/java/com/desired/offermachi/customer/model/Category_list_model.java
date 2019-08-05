package com.desired.offermachi.customer.model;

public class Category_list_model {
    int img;
    String productname,unfolltext;

    public String getUnfolltext() {
        return unfolltext;
    }
    public void setUnfolltext(String unfolltext) {
        this.unfolltext = unfolltext;
    }

    public Category_list_model(int img, String productname, String unfolltext) {
        this.img = img;
        this.productname = productname;
        this.unfolltext = unfolltext;
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
