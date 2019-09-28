package com.desired.offermachi.customer.model;

public class User {
    private String Id;
    private String username,email,mobile,address,gender,dob,profile,SmartShopping,Notificationsound,push_notification_id,Usertype;

    public String getPush_notification_id() {
        return push_notification_id;
    }

    public void setPush_notification_id(String push_notification_id) {
        this.push_notification_id = push_notification_id;
    }

    public User(String  id, String name, String email, String mobile, String address, String gender, String dob, String profile, String smartShopping, String notificationsound,String user_type) {
        this.Id = id;
        this.username = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.gender=gender;
        this.dob=dob;
        this.profile=profile;
        this.SmartShopping=smartShopping;
        this.Notificationsound=notificationsound;
        this.Usertype=user_type;
    }
      /* this.Usertype=user_type;*/

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

    public String getUsertype() {
        return Usertype;
    }
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
