package com.bopuniv.server.repository;

import com.bopuniv.server.entities.Department;
import com.bopuniv.server.entities.Training;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    // ...DepartmentDeptId is similar to department.getDeptId() so it is going to look for the deptId property in department class
    Optional<Training> findByTrainingIdAndDepartmentDeptId(Long trainingId, Long deptId);
    List<Training> findAllByDepartmentDeptId(Long deptId); // find all trainings by department
    List<Training> findAllByDepartmentDeptId(Long deptId, Pageable page);
    List<Training> findAllByDepartmentPtcPtcId(Long ptcId); // find all trainings by ptc
    List<Training> findAllByDepartmentPtcPtcId(Long ptcId, Pageable page);
    List<Training> findAllByDegreeContainingAndDepartmentPtcPtcId(String degree, Long ptcId, Pageable page);
//    List<Training> findAllByDegreeLikeAndDepartmentDeptIdAndDepartmentPtcPtcId(String degree, Long deptId, Long ptcId, Pageable page);
    List<Training> findAllByDepartmentInAndDepartmentPtcPtcId(List<Department> departments, Long ptcId, Pageable page);
    List<Training> findAllByDegreeContainingAndDepartmentInAndDepartmentPtcPtcId(String degree, List<Department> departments, Long ptcId, Pageable page);

    List<Training> findAllByActivityDomainContaining(String activityDomain, Pageable page);
    List<Training> findAllByActivityDomainContainingAndPublishedTrue(String activityDomain, Pageable page);
    List<Training> findAllByPublishedTrue(Pageable page);

    long countByDegreeContainingAndDepartmentPtcPtcId(String degree, Long ptcId);
    long countByDegreeContainingAndDepartmentDeptIdAndDepartmentPtcPtcId(String degree, Long deptId, Long ptcId);
    long countByDepartmentInAndDepartmentPtcPtcId(List<Department> departments, Long ptcId);
    long countByDegreeContainingAndDepartmentInAndDepartmentPtcPtcId(String degree, List<Department> departments, Long ptcId);
    long countByDepartmentDeptId(Long deptId); // count trainings for a department
    long countByDepartmentPtcPtcId(Long ptcId); // count trainings for a Ptc
    void deleteByTrainingIdIn(List<Long> trainingIds); // delete a list of training
    void deleteByTrainingIdInAndDepartmentPtcPtcId(List<Long> trainingIds, Long ptcId); // delete a list of trainings of a Ptc
    void deleteByTrainingIdInAndDepartmentDeptId(List<Long> trainingIds, Long deptId); // delete a list of trainings for a Department
    void deleteByDepartmentPtcPtcId(Long ptcId); // delete all trainings for Ptc
    void deleteByDepartmentDeptId(Long deptId); // delete all trainings for Department
    void deleteByTrainingIdAndDepartmentDeptId(Long trainingId, Long deptId);
    void deleteByTrainingIdAndDepartmentPtcPtcId(Long trainingId, Long ptcId);
}
