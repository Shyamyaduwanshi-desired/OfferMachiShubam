package com.desired.offermachi.customer.model;

public class User {
    private String Id;
    private String username,email,mobile,address,gender,profile,SmartShopping,Notificationsound;
    public User(String  id, String name, String email, String mobile,String address,String gender,String profile,String smartShopping,String notificationsound) {
        this.Id = id;
        this.username = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.gender=gender;
        this.profile=profile;
        this.SmartShopping=smartShopping;
        this.Notificationsound=notificationsound;
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

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getProfile() {
        return profile;
    }

    public String getSmartShopping() {
        return SmartShopping;
    }

    public String getNotificationsound() {
        return Notificationsound;
    }
}
