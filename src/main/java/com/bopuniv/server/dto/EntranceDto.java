package com.bopuniv.server.dto;

import com.bopuniv.server.entities.Entrance;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class EntranceDto {

    private Long entryId;

    @NotNull
    @Size(min = 1, message = "Entrance type can't be empty")
    private String type; // entrance examination, file review...

    private String examFees;

    private LocalDate examDate;

    @NotNull(message = "Please specify a course start date")
    private LocalDate courseStartDate;

    @NotNull(message = "Please specify application deadline start date")
    private LocalDate applicationDeadline;

    private String comment;

    private Long trainingId;

    public EntranceDto(){}

    public EntranceDto(Entrance entrance){
        entryId = entrance.getEntryId();
        type = entrance.getType();
        examDate = entrance.getExamDate();
        examFees = entrance.getExamFees();
        courseStartDate = entrance.getCourseStartDate();
        applicationDeadline = entrance.getApplicationDeadline();
        comment = entrance.getComment();
        trainingId = entrance.getTraining().getTrainingId();
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExamFees() {
        return examFees;
    }

    public void setExamFees(String examFees) {
        this.examFees = examFees;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public LocalDate getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(LocalDate courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    @Override
    public String toString() {
        return "EntranceDto{" +
                "entryId='" + entryId + '\'' +
                ", type='" + type + '\'' +
                ", examFees='" + examFees + '\'' +
                ", examDate=" + examDate +
                ", courseStartDate=" + courseStartDate +
                ", applicationDeadline=" + applicationDeadline +
                ", comment='" + comment + '\'' +
                ", departmentId=" + trainingId +
                '}';
    }
}
