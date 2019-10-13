package com.desired.offermachi.retalier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferLocation {

@SerializedName("id")
@Expose
private String id;
@SerializedName("locality_name")
@Expose
private String localityName;
@SerializedName("locality_slug")
@Expose
private String localitySlug;
@SerializedName("city_name")
@Expose
private String cityName;
@SerializedName("status")
@Expose
private String status;
@SerializedName("created")
@Expose
private String created;
@SerializedName("modified")
@Expose
private String modified;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getLocalityName() {
return localityName;
}

public void setLocalityName(String localityName) {
this.localityName = localityName;
}

public String getLocalitySlug() {
return localitySlug;
}

public void setLocalitySlug(String localitySlug) {
this.localitySlug = localitySlug;
}

public String getCityName() {
return cityName;
}

public void setCityName(String cityName) {
this.cityName = cityName;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getCreated() {
return created;
}

public void setCreated(String created) {
this.created = created;
}

public String getModified() {
return modified;
}

public void setModified(String modified) {
this.modified = modified;
}

}