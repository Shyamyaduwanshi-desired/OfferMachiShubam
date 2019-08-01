package com.desired.offermachi.customer.model;

public class Trending_model {
    int img;
    String productname,productdetails,productdisscount,productdate,productshare;

    public String getProductdate() {
        return productdate;
    }

    public void setProductdate(String productdate) {
        this.productdate = productdate;
    }

    public void setProductshare(String productshare) {
        this.productshare = productshare;
    }

    public String getProductshare() {
        return productshare;
    }

    public Trending_model(int img, String productname, String productdetails, String productdisscount, String productdate, String productshare) {
        this.img = img;
        this.productname = productname;
        this.productdetails = productdetails;
        this.productdisscount = productdisscount;
        this.productdate = productdate;
        this.productshare = productshare;
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

    public void setProductdisscount(String productdisscount) {
        this.productdisscount = productdisscount;
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

    public String getProductdisscount() {
        return productdisscount;
    }


}
