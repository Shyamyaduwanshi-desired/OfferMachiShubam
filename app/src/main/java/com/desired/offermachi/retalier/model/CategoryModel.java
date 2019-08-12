package com.desired.offermachi.retalier.model;

public class CategoryModel {
    private String id,Categoryname,Status;

    public CategoryModel(String id,String categoryname,String status){
        this.id = id;
        this.Categoryname=categoryname;
        this.Status=status;
    }

    public String getId() {
        return id;
    }

    public String getCategoryname() {
        return Categoryname;
    }

    public String getStatus() {
        return Status;
    }
}
