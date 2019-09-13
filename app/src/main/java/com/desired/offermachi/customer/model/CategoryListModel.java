package com.desired.offermachi.customer.model;

public class CategoryListModel {
    private String catid;
    private String catname;
    private String catimage;
    private String Followstatus;
    private String Bannerimage;
    boolean checkStatus;
/*    public CategoryListModel(String catid, String catname, String catimage,String followstatus,String bannerimage) {
        this.catid = catid;
        this.catname = catname;
        this.catimage = catimage;
        this.Followstatus = followstatus;
        this.Bannerimage=bannerimage;
    }*/

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


    public void setCatid(String catid) {
        this.catid = catid;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public void setCatimage(String catimage) {
        this.catimage = catimage;
    }

    public void setFollowstatus(String followstatus) {
        Followstatus = followstatus;
    }

    public void setBannerimage(String bannerimage) {
        Bannerimage = bannerimage;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }
}
