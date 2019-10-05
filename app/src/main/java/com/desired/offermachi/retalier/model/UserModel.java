package com.desired.offermachi.retalier.model;

import android.content.Intent;

public class UserModel {
    private String Id ,categoryId;
    private String username, email,mobile,store_name,store_contact_number,store_address,store_city,store_day,store_opentime,store_closetime,about_store,gender,profile,storeimage,Usertype;

    public UserModel(String  id, String categoryid , String name, String email, String mobile, String store_name, String store_contact_number, String store_address, String store_city, String store_day, String store_opentime, String store_closetime, String about_store, String gender, String profile, String storeimage, String user_type) {
        this.Id = id;
        this.categoryId=categoryid;
        this.username = name;
        this.email = email;
        this.mobile = mobile;
        this.store_name = store_name;
        this.store_contact_number = store_contact_number;
        this.store_address = store_address;
        this.store_city = store_city;
        this.store_day = store_day;
        this.store_opentime = store_opentime;
        this.store_closetime = store_closetime;
        this.about_store = about_store;
        this.gender=gender;
        this.profile=profile;
        this.storeimage = storeimage;
        this.Usertype=user_type;
    }

    public String getId() {
        return Id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStore_contact_number() {
        return store_contact_number;
    }

    public String getStore_address() {
        return store_address;
    }

    public String getStore_city() {
        return store_city;
    }

    public String getStore_day() {
        return store_day;
    }

    public String getStore_opentime() {
        return store_opentime;
    }

    public String getStore_closetime() {
        return store_closetime;
    }

    public String getAbout_store() {
        return about_store;
    }

    public String getGender() {
        return gender;
    }

    public String getProfile() {
        return profile;
    }

    public String getStoreimage() {
        return storeimage;
    }

    public String getUsertype() {
        return Usertype;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


}
