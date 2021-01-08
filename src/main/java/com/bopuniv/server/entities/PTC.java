package com.bopuniv.server.entities;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * PTC: Professional training center. Refers to all the types of training center. University, High School, College, Certification center...
 */
@Entity
@Table(name = "ptc")
public class PTC {

    @Id
    @Column(unique = true, nullable = false, name = "ptc_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ptcId;

    @Column(nullable = false, name = "long_name")
    private String longName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String country;

    @Column(name = "twitter_handler")
    private String twitterHandler;

    @Column(name = "facebook_handler")
    private String facebookHandler;

    @Column(name = "instagram_handler")
    private String instagramHandler;

    @Column(name = "linkedin_handler")
    private String linkedInHandler;

    @Column(name = "website_url")
    private String websiteUrl;

    @Transient
    @OneToMany(mappedBy = "ptc", fetch= FetchType.LAZY)
    private List<Department> departments;

    @Transient
    @OneToMany(mappedBy = "ptc", fetch = FetchType.LAZY)
    private List<Campus> campuses;

    @Transient
    @OneToMany(mappedBy = "ptc", fetch = FetchType.LAZY)
    private List<User> users;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    public PTC(){}

    public PTC(Long ptcId, String longName, String email, String phoneNumber, String country, LocalDateTime createdOn) {
        this.ptcId = ptcId;
        this.longName = longName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.createdOn = createdOn;
    }

    public PTC(String longName, String email){
        this.longName = longName;
        this.email = email;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public Long getPtcId() {
        return ptcId;
    }

    public void setPtcId(Long id) {
        this.ptcId = id;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Campus> getCampuses() {
        return campuses;
    }

    public void setCampuses(List<Campus> campuses) {
        this.campuses = campuses;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PTC)) return false;
        PTC ptc = (PTC) o;
        return email.equals(ptc.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "PTC{" +
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
                ", departments=" + departments +
                ", campuses=" + campuses +
                ", users=" + users +
                '}';
    }
}
