package com.desired.offermachi.retalier.model;

public class AddStoreLocBean {
    String sLocNm,sLocLat,sLocLong,address,phoneNumber;

    public String getsLocNm() {
        return sLocNm;
    }

    public void setsLocNm(String sLocNm) {
        this.sLocNm = sLocNm;
    }

    public String getsLocLat() {
        return sLocLat;
    }

    public void setsLocLat(String sLocLat) {
        this.sLocLat = sLocLat;
    }

    public String getsLocLong() {
        return sLocLong;
    }

    public void setsLocLong(String sLocLong) {
        this.sLocLong = sLocLong;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
