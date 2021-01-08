package com.bopuniv.server.services;

import com.bopuniv.server.entities.Career;
import com.bopuniv.server.entities.Training;

import java.util.List;

public interface ITrainingCareerService {
    List<Training> findAllByCareerId(Long careerId);
    List<Career> findAllByTrainingId(Long trainingId);
    void saveTrainingCareer(Long trainingId, Long careerId, Long deptId);
    void deleteTrainingCareer(Long trainingId, Long careerId, Long deptId);
}
