package com.desired.offermachi.retalier.model;

public class BrandModel {
    private String id,Brandname,Status;

    public BrandModel(String id,String brandname,String status){
        this.id = id;
        this.Brandname=brandname;
        this.Status=status;
    }

    public String getId() {
        return id;
    }

    public String getBrandname() {
        return Brandname;
    }

    public String getStatus() {
        return Status;
    }
}
