package com.bopuniv.server.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Entrance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, name = "entry_id")
    private Long entryId;

    @Column(nullable = false)
    private String type; // entrance examination, file review...

    @Column(name = "exam_fees")
    private String examFees;

    @Column(name="exam_date")
    private LocalDate examDate;

    @Column(name = "course_start_date")
    private LocalDate courseStartDate;

    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    private String comment; // can be used to provide more information

    @ManyToOne(targetEntity = Training.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "training_id")
    private Training training;

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

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    @Override
    public String toString() {
        return "Entrance{" +
                "entryId='" + entryId + '\'' +
                ", type='" + type + '\'' +
                ", examFees='" + examFees + '\'' +
                ", examDate=" + examDate +
                ", courseStartDate=" + courseStartDate +
                ", applicationDeadline=" + applicationDeadline +
                ", comment='" + comment + '\'' +
                '}';
    }
}
