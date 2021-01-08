package com.bopuniv.server.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A training is a path that leads to a qualification such as a master degree, a bachelor degree, BTS, Certification...
 */
@Entity
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, name = "training_id")
    private Long trainingId;

    @Column(nullable = false)
    private String name;

    private String description;

    private String degree; // or qualification

    private String duration;

    private String cost;

    @Column(name = "admission_on")
    private String admissionOn; // exam, file examination, free entry

    private String preRequisite; // Do you need a particular degree to attend this course. e.g. to be accepted for a master degree you need a licence

    /**
     * Used to know if the information related to this training should appear in search results
     */
    @Column(name = "published")
    private boolean published;

    @ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "dept_id")
    private Department department;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    /**
     * The domain of activity this training falls into. Will be useful to search training by career
     */
    @Column(name = "activity_domain", nullable = false)
    private String activityDomain;

    public Training() {
        super();
        this.createdOn = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    public Training(String name, Department department) {
        this.name = name;
        this.department = department;
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

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAdmissionOn() {
        return admissionOn;
    }

    public void setAdmissionOn(String admissionOn) {
        this.admissionOn = admissionOn;
    }

    public String getPreRequisite() {
        return preRequisite;
    }

    public void setPreRequisite(String preRequisite) {
        this.preRequisite = preRequisite;
    }

    public String getActivityDomain() {
        return activityDomain;
    }

    public void setActivityDomain(String activityDomain) {
        this.activityDomain = activityDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Training)) return false;
        Training training = (Training) o;
        return trainingId.equals(training.trainingId) &&
                name.equals(training.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, name);
    }

    @Override
    public String toString() {
        return "Training{" +
                "trainingId=" + trainingId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", degree='" + degree + '\'' +
                ", duration='" + duration + '\'' +
                ", cost='" + cost + '\'' +
                ", admissionOn='" + admissionOn + '\'' +
                ", preRequisite='" + preRequisite + '\'' +
                ", published=" + published +
                ", department=" + department +
                ", createdOn=" + createdOn +
                ", lastModified=" + lastModified +
                ", activityDomain='" + activityDomain + '\'' +
                '}';
    }
}
