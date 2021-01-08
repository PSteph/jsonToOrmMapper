package com.bopuniv.server.services;

import com.bopuniv.server.dto.CareerDto;
import com.bopuniv.server.entities.Career;

import java.util.List;

public interface ICareerService {
    void save(CareerDto careerDto);
    Career findById(Long id);
    void delete(Long trainingId);
    List<Career> findAll();
    Long count();
}
