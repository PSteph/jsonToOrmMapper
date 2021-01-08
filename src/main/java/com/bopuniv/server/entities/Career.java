package com.bopuniv.server.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, name = "career_id")
    private Long careerId;

    @Column(nullable = false)
    private String name;

    private String description;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Career)) return false;
        Career career = (Career) o;
        return careerId.equals(career.careerId) &&
                name.equals(career.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(careerId, name);
    }

    @Override
    public String toString() {
        return "Career{" +
                "id=" + careerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
