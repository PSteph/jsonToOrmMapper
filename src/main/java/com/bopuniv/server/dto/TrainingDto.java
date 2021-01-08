package com.bopuniv.server.dto;

import com.bopuniv.server.entities.Training;

import javax.validation.constraints.NotNull;
import java.util.List;

public class TrainingDto {

    private Long trainingId;

    @NotNull
    private String name;

    private String description;

    private String degree; // or qualification

    private String duration;

    private String cost;

    private String admissionOn; // exam, file examination, free entry

    private Long deptId;

    private boolean published;

    private String preRequisite; // Do you need a particular degree to attend this course. e.g. to be accepted for a master degree you need a licence

    private List<Long> careerIds;

    private PTCDto ptc;

    @NotNull
    private String activityDomain;

    public TrainingDto(){}

    public TrainingDto(Training training){
        trainingId = training.getTrainingId();
        name = training.getName();
        description = training.getDescription();
        degree = training.getDegree();
        duration = training.getDuration();
        cost = training.getCost();
        admissionOn = training.getAdmissionOn();
        deptId = training.getDepartment().getDeptId();
        published = training.isPublished();
        preRequisite = training.getPreRequisite();
        activityDomain = training.getActivityDomain();
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<Long> getCareerIds() {
        return careerIds;
    }

    public void setCareerIds(List<Long> careerIds) {
        this.careerIds = careerIds;
    }

    public String getPreRequisite() {
        return preRequisite;
    }

    public void setPreRequisite(String preRequisite) {
        this.preRequisite = preRequisite;
    }

    public PTCDto getPtc() {
        return ptc;
    }

    public void setPtc(PTCDto ptc) {
        this.ptc = ptc;
    }

    public String getActivityDomain() {
        return activityDomain;
    }

    public void setActivityDomain(String activityDomain) {
        this.activityDomain = activityDomain;
    }

    @Override
    public String toString() {
        return "TrainingDto{" +
                "trainingId=" + trainingId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", degree='" + degree + '\'' +
                ", duration='" + duration + '\'' +
                ", cost='" + cost + '\'' +
                ", admissionOn='" + admissionOn + '\'' +
                ", deptId=" + deptId +
                ", published=" + published +
                ", preRequisite='" + preRequisite + '\'' +
                ", careerIds=" + careerIds +
                ", ptc=" + ptc +
                ", activityDomain='" + activityDomain + '\'' +
                '}';
    }
}
