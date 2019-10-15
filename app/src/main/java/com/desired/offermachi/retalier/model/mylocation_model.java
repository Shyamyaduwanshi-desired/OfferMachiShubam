package com.desired.offermachi.retalier.model;

public class mylocation_model {
    private String id;
    private String locality_name;
    private String location_address;
    private String locationpersonmobileno;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocality_name() {
        return locality_name;
    }

    public void setLocality_name(String locality_name) {
        this.locality_name = locality_name;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }



    public String getLocationpersonmobileno() {
        return locationpersonmobileno;
    }

    public void setLocationpersonmobileno(String locationpersonmobileno) {
        this.locationpersonmobileno = locationpersonmobileno;
    }

    public mylocation_model(String id,String locality_name ,String location_address, String locationpersonmobileno) {
        this.id = id;
        this.locality_name=locality_name;
        this.location_address = location_address;
        this .locationpersonmobileno =locationpersonmobileno;

    }


}
