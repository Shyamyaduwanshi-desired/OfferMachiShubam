package com.desired.offermachi.customer.model;

public class StoreModel {
    private String Id,Storename,Storeimage,StoreCategory,StoreFav/*,address*/;

    public StoreModel(String id,String storename,String storeimage,String storeCategory,String storeFav) {
        this.Id = id;
        this.Storename = storename;
        this.Storeimage=storeimage;
        this.StoreCategory=storeCategory;
        this.StoreFav=storeFav;
//        this.address=storeFav;
    }

    public String getId() {
        return Id;
    }

    public String getStorename() {
        return Storename;
    }

    public String getStoreimage() {
        return Storeimage;
    }

    public String getStoreCategory() {
        return StoreCategory;
    }

    public String getStoreFav() {
        return StoreFav;
    }
}
