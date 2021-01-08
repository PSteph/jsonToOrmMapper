package com.bopuniv.server.dto;

import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

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

    @ValidEmail
    private String email;

    private String phoneNumber = "";

    private String country = "";

    private String twitterHandler = "";

    private String facebookHandler = "";

    private String instagramHandler = "";

    private String linkedInHandler = "";

    private String websiteUrl = "";


    public PTCDto(){}

    public PTCDto(PTC ptc){
        email = ptc.getEmail();
        country = ptc.getCountry();
        facebookHandler = ptc.getFacebookHandler();
        instagramHandler = ptc.getInstagramHandler();
        linkedInHandler = ptc.getLinkedInHandler();
        twitterHandler = ptc.getTwitterHandler();
        websiteUrl = ptc.getWebsiteUrl();
        logoUrl = ptc.getLogoUrl();
        longName = ptc.getLongName();
        phoneNumber = ptc.getPhoneNumber();
        ptcId = ptc.getPtcId();
        shortName = ptc.getShortName();
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

    @Override
    public String toString() {
        return "PTCDto{" +
                "ptcId=" + ptcId +
                ", longName='" + longName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", country='" + country + '\'' +
                ", twitterHandler='" + twitterHandler + '\'' +
                ", facebookHandler='" + facebookHandler + '\'' +
                ", instagramHandler='" + instagramHandler + '\'' +
                ", linkedInHandler='" + linkedInHandler + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
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
