package com.desired.offermachi.customer.model;

public class NotificationModel {
    String title,msg ,custom_offertype;

    public String getCustom_offertype() {
        return custom_offertype;
    }

    public void setCustom_offertype(String custom_offertype) {
        this.custom_offertype = custom_offertype;
    }

    public NotificationModel(String title, String msg, String custom_offertype) {
        this.title = title;
        this.msg = msg;
        this.custom_offertype=custom_offertype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
