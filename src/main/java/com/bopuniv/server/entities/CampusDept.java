package com.bopuniv.server.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "campus_dept")
public class CampusDept {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name="campus_id")
    private Campus campus;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CampusDept)) return false;
        CampusDept that = (CampusDept) o;
        return id.equals(that.id) &&
                campus.equals(that.campus) &&
                department.equals(that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, campus, department);
    }

    @Override
    public String toString() {
        return "CampusDept{" +
                "id=" + id +
                ", campus=" + campus +
                ", department=" + department +
                '}';
    }
}
