package com.desired.offermachi.customer.model;

public class muliplelocationshowmodel {
    private String id;
    private String locality_name;
    private String location_address;
    private String location_mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocality_name() {
        return locality_name;
    }

    public void setLocality_name(String locality_name) {
        this.locality_name = locality_name;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public String getLocation_mobile() {
        return location_mobile;
    }

    public void setLocation_mobile(String location_mobile) {
        this.location_mobile = location_mobile;
    }

    public muliplelocationshowmodel(String id, String locality_name , String location_address, String location_mobile) {
        this.id = id;
        this.locality_name=locality_name;
        this.location_address = location_address;
        this.location_mobile=location_mobile;

    }

}
