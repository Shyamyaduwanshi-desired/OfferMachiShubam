package com.desired.offermachi.retalier.model;

public class mylocation_model {
    private String id;
    private String location_address;
    private String location_contact_persion;
    private String locationpersonmobileno;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public String getLocation_contact_persion() {
        return location_contact_persion;
    }

    public void setLocation_contact_persion(String location_contact_persion) {
        this.location_contact_persion = location_contact_persion;
    }

    public String getLocationpersonmobileno() {
        return locationpersonmobileno;
    }

    public void setLocationpersonmobileno(String locationpersonmobileno) {
        this.locationpersonmobileno = locationpersonmobileno;
    }

    public mylocation_model(String id, String location_address, String location_contact_persion , String locationpersonmobileno) {
        this.id = id;
        this.location_address = location_address;
        this.location_contact_persion = location_contact_persion;
        this .locationpersonmobileno =locationpersonmobileno;

    }


}
