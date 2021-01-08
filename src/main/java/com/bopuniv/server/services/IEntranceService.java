package com.bopuniv.server.services;

import com.bopuniv.server.dto.EntranceDto;
import com.bopuniv.server.entities.Entrance;
import com.bopuniv.server.entities.Training;

import java.util.List;

public interface IEntranceService {

    Entrance save(EntranceDto entranceDto);
    Entrance update(EntranceDto entranceDto);
    Entrance findById(Long id);
    void delete(Long entranceId);
    List<Entrance> findAll();
    List<Entrance> findAllByTraining(Training training);
    Long count();
}
