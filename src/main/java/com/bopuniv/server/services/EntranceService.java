package com.bopuniv.server.services;

import com.bopuniv.server.dto.EntranceDto;
import com.bopuniv.server.entities.Entrance;
import com.bopuniv.server.entities.Training;
import com.bopuniv.server.exceptions.EntranceException;
import com.bopuniv.server.exceptions.TrainingException;
import com.bopuniv.server.repository.EntranceRepository;
import com.bopuniv.server.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntranceService implements IEntranceService {

    @Autowired
    private EntranceRepository repository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Entrance save(EntranceDto entranceDto) {
        Optional<Training> trainingOptional = trainingRepository.findById(entranceDto.getTrainingId());
        if(trainingOptional.isEmpty())
            throw new TrainingException();

        Entrance entrance = new Entrance();
        entrance.setApplicationDeadline(entranceDto.getApplicationDeadline());
        entrance.setComment(entranceDto.getComment());
        entrance.setCourseStartDate(entranceDto.getCourseStartDate());
        entrance.setExamDate(entranceDto.getExamDate());
        entrance.setExamFees(entranceDto.getExamFees());
        entrance.setTraining(trainingOptional.get());
        entrance.setType(entranceDto.getType());

        repository.save(entrance);
        return entrance;
    }

    @Override
    public Entrance update(EntranceDto entranceDto) {
        Optional<Training> trainingOptional = trainingRepository.findById(entranceDto.getTrainingId());

        if(trainingOptional.isEmpty())
            throw new TrainingException();

        Optional<Entrance> entranceOptional = repository.findById(entranceDto.getEntryId());

        if(entranceOptional.isEmpty())
            throw new EntranceException();

        Entrance entrance = entranceOptional.get();
        entrance.setApplicationDeadline(entranceDto.getApplicationDeadline());
        entrance.setComment(entranceDto.getComment());
        entrance.setCourseStartDate(entranceDto.getCourseStartDate());
        entrance.setExamDate(entranceDto.getExamDate());
        entrance.setExamFees(entranceDto.getExamFees());
        entrance.setType(entranceDto.getType());

        repository.save(entrance);
        return entrance;
    }

    @Override
    public Entrance findById(Long entranceId) {
        Optional<Entrance> entranceOptional = repository.findById(entranceId);

        if(entranceOptional.isEmpty())
            throw new EntranceException();

        return entranceOptional.get();
    }

    @Override
    public void delete(Long entranceId) {
        Optional<Entrance> entranceOptional = repository.findById(entranceId);

        if(entranceOptional.isEmpty())
            throw new EntranceException();

        repository.deleteById(entranceId);
    }

    @Override
    public List<Entrance> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Entrance> findAllByTraining(Training training){

        return repository.findAllByTraining(training);
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
