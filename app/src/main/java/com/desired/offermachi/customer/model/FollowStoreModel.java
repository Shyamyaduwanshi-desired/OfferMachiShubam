package com.desired.offermachi.customer.model;

public class FollowStoreModel {
    private String Id,Storename,Storeimage,StoreCategory,StoreFav,Storeabout;

    public FollowStoreModel(String id,String storename,String storeimage,String storeCategory,String storeFav,String storeabout) {
        this.Id = id;
        this.Storename = storename;
        this.Storeimage=storeimage;
        this.StoreCategory=storeCategory;
        this.StoreFav=storeFav;
        this.Storeabout=storeabout;
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

    public String getStoreabout() {
        return Storeabout;
    }
}
