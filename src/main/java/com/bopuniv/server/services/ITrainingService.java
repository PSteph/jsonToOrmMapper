package com.bopuniv.server.services;

import com.bopuniv.server.dto.TrainingDto;
import com.bopuniv.server.entities.Training;

import java.awt.print.Pageable;
import java.util.List;

public interface ITrainingService {
    TrainingDto save(TrainingDto trainingDto);
    TrainingDto update(TrainingDto trainingDto, Long deptId);
    Training findById(Long id);
    Training findByTrainingIdAndDeptId(Long trainingId, Long deptId);
    List<Training> findAllByDeptId(Long deptId);
    List<Training> searchByDeptIdPaging(Long deptId, Long limit, Long offset);
    List<Training> searchByPtcIdPaging(Long ptcId, Long limit, Long offset);
    List<Training> searchByDegreeAndDepartmentIdAndCampusIdAndPtcIdPaging(String degree, Long deptId, Long campusId, Long ptcId, Long limit, Long offset);
    List<Training> searchByActivityDomainPaging(Long limit, Long offset, String activityDomain);
    List<Training> searchByActivityDomainAndPublishedPaging(Long limit, Long offset, String activityDomain);
    List<Training> searchAllPage(Long limit, Long offset);
    List<Training> searchAllPublishedPage(Long limit, Long offset);
    Long countByDegreeAndDepartmentIdAndCampusIdAndPtcIdPaging(String degree, Long deptId, Long campusId, Long ptcId);
    void delete(Long trainingId);
    void deleteTrainings(List<Long> trainingIds);
    void deleteByTrainingsAndDept(List<Long> trainingIds, Long deptId);
    void deleteByTrainingsAndPtc(List<Long> trainingIds, Long ptcId);
    void deleteAllTrainingsByDepartment(Long deptId);
    void deleteAllTrainingsByPtc(Long ptcId);
    void deleteByTrainingIdAndPtcId(Long trainingId, Long ptcId);
    void deleteByTrainingIdAndDeptId(Long trainingId, Long deptId);
    List<Training> findAll();
    Long count();
    Long countByPtcId(Long ptcId);
    Long countByDeptId(Long deptId);
}
