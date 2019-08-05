package com.desired.offermachi.customer.model;

public class filter_show_model {
    int img;
    String productname;


    public filter_show_model(int img, String productname) {
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
