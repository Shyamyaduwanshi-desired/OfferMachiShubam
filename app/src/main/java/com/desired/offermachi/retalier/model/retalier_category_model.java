package com.desired.offermachi.retalier.model;

public class retalier_category_model {
    int img;
    String productname,productdetails,productcontent,productdate,productshare;


    public retalier_category_model(int img, String productname, String productdate) {
        this.img = img;
        this.productname = productname;
        this.productdate = productdate;
    }
    public void setImg(int img) {
        this.img = img;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setProductdetails(String productdetails) {
        this.productdetails = productdetails;
    }

    public int getImg() {
        return img;
    }

    public String getProductname() {
        return productname;
    }

    public String getProductdetails() {
        return productdetails;
    }
    public void setProductdate(String productdate) {
        this.productdate = productdate;
    }

    public void setProductshare(String productshare) {
        this.productshare = productshare;
    }

    public String getProductdate() {
        return productdate;
    }

    public String getProductshare() {
        return productshare;
    }

}
