package com.desired.offermachi.customer.model;

public class hours_model {
    public String Id;
    public String tittle;
    public boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public hours_model(String Id , String tittle ,  boolean selected) {
        this.Id = Id;
        this.tittle = tittle;
        this .selected = selected;

    }
}
