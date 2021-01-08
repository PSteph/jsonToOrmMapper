package com.bopuniv.server.dto;

import com.bopuniv.server.entities.Campus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CampusDto {

    private Long campusId;

    @NotNull
    @Size(min = 1)
    private String name;

    private String phoneNumber;

    private String latitude;

    private String longitude;

    private boolean isMain;

    private String description;

    @NotNull
    @Size(min = 1)
    private String city;

    @NotNull
    @Size(min = 1)
    private String country;

    private Long ptcId;

    public CampusDto(){}

    public CampusDto(Campus campus){
        campusId = campus.getCampusId();
        name = campus.getName();
        phoneNumber = campus.getPhoneNumber();
        latitude = campus.getLatitude();
        longitude = campus.getLongitude();
        isMain = campus.isMain();
        description = campus.getDescription();
        city = campus.getCity();
        country = campus.getCountry();
        ptcId = campus.getPtc().getPtcId();
    }

    public Long getCampusId() {
        return campusId;
    }

    public void setCampusId(Long campusId) {
        this.campusId = campusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPtcId() {
        return ptcId;
    }

    public void setPtcId(Long ptcId) {
        this.ptcId = ptcId;
    }

    @Override
    public String toString() {
        return "CampusDto{" +
                "campusId=" + campusId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", isMain=" + isMain +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", ptcId=" + ptcId +
                '}';
    }
}
