package com.desired.offermachi.customer.constant;

public class hand {
    private static hand ha = new hand();

    public static hand getintance() {
        return ha;
    }
    String catid,catname,catimage;
    public hand() {

    }


    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCatimage() {
        return catimage;
    }

    public void setCatimage(String catimage) {
        this.catimage = catimage;
    }
}
