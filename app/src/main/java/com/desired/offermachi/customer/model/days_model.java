package com.desired.offermachi.customer.model;

public class days_model {
    String id;
    String tittle;
    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

  /*  public days_model(String Id , String tittle , boolean selected) {
        this.id = Id;
        this.tittle = tittle;
        this .selected = selected;

    }*/
}
