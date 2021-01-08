package com.bopuniv.server.repository;

import com.bopuniv.server.entities.Entrance;
import com.bopuniv.server.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntranceRepository extends JpaRepository<Entrance, Long> {
    List<Entrance> findAllByTraining(Training training);
}
