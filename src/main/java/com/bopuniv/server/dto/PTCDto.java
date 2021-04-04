package com.bopuniv.server.dto;

import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class PTCDto {

    private Long ptcId;

    @NotNull
    @Size(min=1)
    private String longName;

    private String shortName = "";

    private String logoUrl;

    private String logoName;

    private String description;

    @ValidEmail
    private String email;

    private String phoneNumber = "";

    private String country = "";

    private String twitterHandler = "";

    private String facebookHandler = "";

    private String instagramHandler = "";

    private String linkedInHandler = "";

    private String websiteUrl = "";

    private String latitude;

    private String longitude;

    public PTCDto(){}

    public PTCDto(PTC ptc){
        email = ptc.getEmail();
        country = ptc.getCountry() == null ? "":ptc.getCountry();
        facebookHandler = ptc.getFacebookHandler() == null ? "":ptc.getFacebookHandler();
        instagramHandler = ptc.getInstagramHandler() == null ? "":ptc.getInstagramHandler();
        linkedInHandler = ptc.getLinkedInHandler() == null ? "":ptc.getLinkedInHandler();
        twitterHandler = ptc.getTwitterHandler() == null ? "":ptc.getTwitterHandler();
        websiteUrl = ptc.getWebsiteUrl() == null ? "":ptc.getWebsiteUrl();
        logoUrl = ptc.getLogoUrl() == null ? "":ptc.getLogoUrl();
        longName = ptc.getLongName() == null ? "":ptc.getLongName();
        phoneNumber = ptc.getPhoneNumber() == null ? "":ptc.getPhoneNumber();
        ptcId = ptc.getPtcId();
        shortName = ptc.getShortName() == null ? "":ptc.getShortName();
        description = ptc.getDescription() == null ? "":ptc.getDescription();
        logoName = ptc.getLogoName() == null ? "":ptc.getLogoName();
        latitude = ptc.getLatitude() == null ? "":ptc.getLatitude();
        longitude = ptc.getLongitude() == null ? "":ptc.getLongitude();
    }
    public Long getPtcId() {
        return ptcId;
    }

    public void setPtcId(Long ptcId) {
        this.ptcId = ptcId;
    }

    @NotNull
    public String getLongName() {
        return longName;
    }

    public void setLongName(@NotNull String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTwitterHandler() {
        return twitterHandler;
    }

    public void setTwitterHandler(String twitterHandler) {
        this.twitterHandler = twitterHandler;
    }

    public String getFacebookHandler() {
        return facebookHandler;
    }

    public void setFacebookHandler(String facebookHandler) {
        this.facebookHandler = facebookHandler;
    }

    public String getInstagramHandler() {
        return instagramHandler;
    }

    public void setInstagramHandler(String instagramHandler) {
        this.instagramHandler = instagramHandler;
    }

    public String getLinkedInHandler() {
        return linkedInHandler;
    }

    public void setLinkedInHandler(String linkedInHandler) {
        this.linkedInHandler = linkedInHandler;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
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

    @Override
    public String toString() {
        return "PTCDto{" +
                "ptcId=" + ptcId +
                ", longName='" + longName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", logoName='" + logoName + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", country='" + country + '\'' +
                ", twitterHandler='" + twitterHandler + '\'' +
                ", facebookHandler='" + facebookHandler + '\'' +
                ", instagramHandler='" + instagramHandler + '\'' +
                ", linkedInHandler='" + linkedInHandler + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PTCDto)) return false;
        PTCDto ptcDto = (PTCDto) o;
        return Objects.equals(ptcId, ptcDto.ptcId) &&
                Objects.equals(longName, ptcDto.longName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ptcId, longName);
    }
}
