package com.bopuniv.server.repository;

import com.bopuniv.server.entities.CampusDept;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CampusDeptRepository extends JpaRepository<CampusDept, Long> {

    List<CampusDept> findAllByDepartmentDeptId(Long deptId);
    List<CampusDept> findAllByCampusCampusId(Long campusId);
    List<CampusDept> findAllByCampusCampusId(Long campusId, Pageable page);
    Optional<CampusDept> findByCampusCampusIdAndDepartmentDeptId(Long campusId, Long deptId);
    long countByCampusCampusId(Long campusId);
}
