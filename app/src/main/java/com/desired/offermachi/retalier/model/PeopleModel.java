package com.desired.offermachi.retalier.model;

public class PeopleModel {
    private String peoplename,peopleimage,couponcode,couponimage;
    public PeopleModel(String peoplename, String peopleimage, String couponcode, String couponimage) {
        this.peoplename = peoplename;
        this.peopleimage = peopleimage;
        this.couponcode = couponcode;
        this.couponimage = couponimage;
    }

    public String getPeoplename() {
        return peoplename;
    }

    public String getPeopleimage() {
        return peopleimage;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public String getCouponimage() {
        return couponimage;
    }
}
