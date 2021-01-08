package com.bopuniv.server.repository;

import com.bopuniv.server.entities.Department;
import com.bopuniv.server.entities.PTC;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByPtc(PTC ptc);
    List<Department> findAllByPtcPtcId(Long ptcId);
    List<Department> findAllByPtcPtcId(Long ptcId, Pageable pageable);
    Optional<Department> findByDeptIdAndPtcPtcId(Long deptId, Long ptcId);
    Optional<Department> findByDeptIdAndPtc(Long deptId, PTC ptcId);

    long countByPtcPtcId(Long ptcId);
    void deleteByDeptIdIn(List<Long> deptIds);
    void deleteByDeptIdInAndPtcPtcId(List<Long> deptIds, Long ptcId);
    void deleteByDeptIdAndPtcPtcId(Long deptId, Long ptcId);
    void deleteByPtcPtcId(Long ptcId);
}
