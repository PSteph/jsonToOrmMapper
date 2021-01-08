package com.bopuniv.server.dto;

import com.bopuniv.server.entities.Career;

import javax.validation.constraints.NotNull;

public class CareerDto {

    private Long careerId;

    @NotNull
    private String name;

    private String description;

    public CareerDto(){}

    public CareerDto(Career career){
        careerId = career.getCareerId();
        name = career.getName();
        description = career.getDescription();
    }

    public Long getCareerId() {
        return careerId;
    }

    public void setCareerId(Long careerId) {
        this.careerId = careerId;
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

    @Override
    public String toString() {
        return "CareerDto{" +
                "careerId=" + careerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
