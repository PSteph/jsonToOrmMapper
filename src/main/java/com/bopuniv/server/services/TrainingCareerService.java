package com.bopuniv.server.services;

import com.bopuniv.server.entities.CampusDept;
import com.bopuniv.server.entities.Career;
import com.bopuniv.server.entities.Training;
import com.bopuniv.server.entities.TrainingCareer;
import com.bopuniv.server.exceptions.CareerException;
import com.bopuniv.server.exceptions.TrainingException;
import com.bopuniv.server.repository.CareerRepository;
import com.bopuniv.server.repository.TrainingCareerRepository;
import com.bopuniv.server.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingCareerService implements ITrainingCareerService {

    @Autowired
    TrainingCareerRepository repository;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    CareerRepository careerRepository;

    @Override
    public List<Training> findAllByCareerId(Long careerId) {
        List<TrainingCareer> trainingCareers = repository.findAllByCareerCareerId(careerId);
        return trainingCareers.stream()
                .map(TrainingCareer::getTraining)
                .collect(Collectors.toList());
    }

    @Override
    public List<Career> findAllByTrainingId(Long trainingId) {
        List<TrainingCareer> trainingCareers = repository.findAllByTrainingTrainingId(trainingId);
        return trainingCareers.stream()
                .map(TrainingCareer::getCareer)
                .collect(Collectors.toList());
    }

    @Override
    public void saveTrainingCareer(Long trainingId, Long careerId, Long deptId) {

        Optional<Training> trainingOptional = trainingRepository
                .findByTrainingIdAndDepartmentDeptId(trainingId, deptId);
        if(trainingOptional.isEmpty())
            throw new TrainingException();

        Optional<Career> careerOptional = careerRepository.findById(careerId);

        if(careerOptional.isEmpty())
            throw new CareerException();

        TrainingCareer trainingCareer = new TrainingCareer();
        trainingCareer.setCareer(careerOptional.get());
        trainingCareer.setTraining(trainingOptional.get());
        repository.save(trainingCareer);
    }

    @Override
    public void deleteTrainingCareer(Long trainingId, Long careerId, Long deptId) {

        Optional<Training> trainingOptional = trainingRepository
                .findByTrainingIdAndDepartmentDeptId(trainingId, deptId);

        if(trainingOptional.isEmpty())
            throw new TrainingException();

        Optional<Career> careerOptional = careerRepository.findById(careerId);

        if(careerOptional.isEmpty())
            throw new CareerException();

        TrainingCareer trainingCareer = new TrainingCareer();
        trainingCareer.setCareer(careerOptional.get());
        trainingCareer.setTraining(trainingOptional.get());
        repository.delete(trainingCareer);
    }
}
