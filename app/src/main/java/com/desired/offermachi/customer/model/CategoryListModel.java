package com.desired.offermachi.customer.model;

public class CategoryListModel {
    private String catid;
    private String catname;
    private String catimage;
    private String Followstatus;
    private String Bannerimage;
    public CategoryListModel(String catid, String catname, String catimage,String followstatus,String bannerimage) {
        this.catid = catid;
        this.catname = catname;
        this.catimage = catimage;
        this.Followstatus = followstatus;
        this.Bannerimage=bannerimage;
    }

    public String getCatid() {
        return catid;
    }

    public String getCatname() {
        return catname;
    }

    public String getCatimage() {
        return catimage;
    }

    public String getFollowstatus() {
        return Followstatus;
    }

    public String getBannerimage() {
        return Bannerimage;
    }
}
