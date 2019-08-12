package com.desired.offermachi.retalier.model;

public class OfferTypeModel {
    private String id,Offertype,Status;
    public OfferTypeModel(String id,String offertype,String status){
        this.id = id;
        this.Offertype=offertype;
        this.Status=status;
    }

    public String getId() {
        return id;
    }

    public String getOffertype() {
        return Offertype;
    }

    public String getStatus() {
        return Status;
    }
}

