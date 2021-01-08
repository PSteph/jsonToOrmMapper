package com.bopuniv.server.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * A user can be given permission to manage a department. There is a many to many relationship between
 * Department and users. Permission is the resulting table
 */
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, name = "permission_id")
    private Long permissionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(o instanceof Permission)) return false;
        Permission that = (Permission) o;
        return permissionId.equals(that.permissionId) &&
                user.equals(that.user) &&
                department.equals(that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId, user, department);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", user=" + user +
                ", department=" + department +
                '}';
    }
}
