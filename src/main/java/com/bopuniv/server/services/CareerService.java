package com.bopuniv.server.services;

import com.bopuniv.server.dto.CareerDto;
import com.bopuniv.server.entities.Career;
import com.bopuniv.server.exceptions.CareerException;
import com.bopuniv.server.repository.CareerRepository;
import com.bopuniv.server.repository.TrainingCareerRepository;
import com.bopuniv.server.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CareerService implements ICareerService {

    @Autowired
    CareerRepository repository;

//    @Autowired
//    TrainingRepository trainingRepository;

    @Autowired
    TrainingCareerRepository trainingCareerRepository;

    @Override
    public void save(CareerDto careerDto) {
        Career career =  new Career();
        career.setName(careerDto.getName());
        career.setDescription(careerDto.getDescription());
        repository.save(career);
    }

    @Override
    public Career findById(Long id) {
        Optional<Career> careerOptional = repository.findById(id);
        if(careerOptional.isEmpty())
            throw new CareerException();
        return careerOptional.get();
    }

    @Override
    public void delete(Long careerId) {
        repository.deleteById(careerId);
    }

    @Override
    public List<Career> findAll() {
        return repository.findAll();
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
