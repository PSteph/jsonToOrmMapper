package com.bopuniv.server.services;

import com.bopuniv.server.entities.Campus;
import com.bopuniv.server.entities.Department;

import java.util.List;

public interface ICampusDeptService {
    // all the search should return a list for consistency. THis applies even to search that return only one element
    // such as searchDeptByCampusIdDeptId
    List<Department> findAllDeptByCampusId(Long campusId);
    List<Department> searchDeptByCampusIdPaging(Long campusId, Long limit, Long offset);
    long countAllDeptByCampusId(Long campusId);
    Department findDeptByCampusIdDeptId(Long campusId, Long deptId);
    List<Department> searchDeptByCampusIdDeptId(Long campusId, Long deptId);
    List<Campus> findAllCampusesOfDept(Long deptId);
    void saveCampusDept(Long campusId, Long deptId);
    void deleteCampusDept(Long campusId, Long deptId);

}
