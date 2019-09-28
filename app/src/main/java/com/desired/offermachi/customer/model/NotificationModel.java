package com.desired.offermachi.customer.model;

public class NotificationModel {
    String title,msg ,is_open,custom_offertype ,notiId;

    public String getCustom_offertype() {
        return custom_offertype;
    }

    public void setCustom_offertype(String custom_offertype) {
        this.custom_offertype = custom_offertype;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public NotificationModel(String id,String title, String msg, String is_open, String custom_offertype) {
        this.notiId = id;
        this.title = title;
        this.msg = msg;
        this.is_open=is_open;
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

    public String getNotiId() {
        return notiId;
    }

    public void setNotiId(String notiId) {
        this.notiId = notiId;
    }
}
