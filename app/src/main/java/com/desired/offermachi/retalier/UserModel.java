package com.desired.offermachi.retalier;

import android.content.Intent;

public class UserModel {
    private Integer Id;
    private String username, email,mobile,sote_name,store_contact_number,store_address,store_city,day_hours,about_store;

    public UserModel(int id, String name, String email, String mobile, String sote_name, String store_contact_number, String store_address, String store_city, String day_hours, String about_store) {
        this.Id = id;
        this.username = name;
        this.email = email;
        this.mobile = mobile;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return mobile;
    }
    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        this.username = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setSote_name(String sote_name) {
        this.sote_name = sote_name;
    }

    public void setStore_contact_number(String store_contact_number) {
        this.store_contact_number = store_contact_number;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public void setStore_city(String store_city) {
        this.store_city = store_city;
    }

    public void setDay_hours(String day_hours) {
        this.day_hours = day_hours;
    }

    public void setAbout_store(String about_store) {
        this.about_store = about_store;
    }


    public String getMobile() {
        return mobile;
    }

    public String getSote_name() {
        return sote_name;
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

    public String getDay_hours() {
        return day_hours;
    }

    public String getAbout_store() {
        return about_store;
    }
}
