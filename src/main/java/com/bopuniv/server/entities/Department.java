package com.bopuniv.server.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, name = "dept_id")
    private Long deptId;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(targetEntity = PTC.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "ptc_id")
    private PTC ptc;

    @Transient
    @OneToMany(mappedBy = "department")
    private List<Training> trainings;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    /**
     * Used to know if the information related to this department should appear in search results
     */
    private boolean published;

    public Department(){}

    public Department(String name, String description, PTC ptc){
        this.name = name;
        this.description = description;
        this.ptc = ptc;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public PTC getPtc() {
        return ptc;
    }

    public void setPtc(PTC ptc) {
        this.ptc = ptc;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return deptId.equals(that.deptId) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptId, name);
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ptc=" + ptc +
                ", trainings=" + trainings +
                ", published=" + published +
                '}';
    }
}
