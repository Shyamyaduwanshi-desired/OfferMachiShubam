package com.desired.offermachi.retalier.model;

public class AddStoreLocBean extends CityBean {
    String sLocNm,sLocLat,sLocLong,address,phoneNumber,personNm,locId;

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

    public String getPersonNm() {
        return personNm;
    }

    public void setPersonNm(String personNm) {
        this.personNm = personNm;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }
}
