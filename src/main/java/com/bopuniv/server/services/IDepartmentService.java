package com.bopuniv.server.services;

import com.bopuniv.server.dto.DepartmentDto;
import com.bopuniv.server.entities.Department;
import com.bopuniv.server.entities.PTC;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    Department save(DepartmentDto departmentDto);
    Department update(DepartmentDto departmentDto);
    Department findById(Long id);
    void delete(Long deptId);
    void deleteByDeptIdsAndPtcId(List<Long> deptIds, Long ptcId);
    List<Department> findAll();
    List<Department> findAllByPtcId(Long ptcId);
    List<Department> searchByPtcIdPaging(Long ptcId, Long limit, Long offset);
    long countAllByPtcId(Long ptcId);
    Department findByDeptIdAndPtcId(Long deptId, Long ptcId);
    Long count();
}
