package com.desired.offermachi.customer.model;

public class slider_model {
    int img;
    String productname;

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductname() {
        return productname;
    }

    public slider_model(int img , String productname) {
        this.img = img;
        this.productname=productname;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

}
