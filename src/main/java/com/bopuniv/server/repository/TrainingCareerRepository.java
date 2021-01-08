package com.bopuniv.server.repository;

import com.bopuniv.server.entities.TrainingCareer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingCareerRepository extends JpaRepository<TrainingCareer, Long> {

    List<TrainingCareer> findAllByTrainingTrainingId(Long trainingId);
    List<TrainingCareer> findAllByCareerCareerId(Long careerId);
    Optional<TrainingCareer> findByTrainingTrainingIdAndCareerCareerId(Long trainingId, Long careerId);
}
