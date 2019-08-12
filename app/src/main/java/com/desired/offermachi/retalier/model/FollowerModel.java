package com.desired.offermachi.retalier.model;

import java.io.Serializable;

public class FollowerModel implements Serializable {
    private boolean isChecked = false;
    private String id,Followername;
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public FollowerModel(String id,String followername){
        this.id = id;
        this.Followername=followername;

    }

    public String getId() {
        return id;
    }

    public String getFollowername() {
        return Followername;
    }
}
