package com.bopuniv.server.dto;

import com.bopuniv.server.entities.Department;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class DepartmentDto {

    private Long deptId;

    @NotNull
    @Size(min = 1, message = "Department name cannot be empty")
    private String name;

    private String description;

    private Long ptcId;

    private boolean published;

    private List<Long> campusIds;

    public DepartmentDto(){}

    public DepartmentDto(Department department){
        deptId = department.getDeptId();
        name = department.getName();
        description = department.getDescription();
        ptcId = department.getPtc().getPtcId();
        published = department.isPublished();
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

    public Long getPtcId() {
        return ptcId;
    }

    public void setPtcId(Long ptcId) {
        this.ptcId = ptcId;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<Long> getCampusIds() {
        return campusIds;
    }

    public void setCampusIds(List<Long> campusIds) {
        this.campusIds = campusIds;
    }

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "deptId=" + deptId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ptcId=" + ptcId +
                ", published=" + published +
                ", campusIds=" + campusIds +
                '}';
    }
}
