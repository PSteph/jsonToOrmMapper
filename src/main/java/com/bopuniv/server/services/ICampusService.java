package com.bopuniv.server.services;

import com.bopuniv.server.dto.CampusDto;
import com.bopuniv.server.entities.Campus;
import com.bopuniv.server.entities.Department;
import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.entities.User;

import java.util.List;

public interface ICampusService {

    CampusDto save(CampusDto campusDto);
    CampusDto update(CampusDto campusDto);
    Campus findById(Long id);
    Campus findByCampusIdAndPtcId(Long campusId, Long ptcId);
    Campus findByCampusIdAndPtc(Long campusId, PTC ptc);
    List<Campus> findAllByPtcId(Long ptcId);
    List<Campus> findAllByPtc(PTC ptc);
    void delete(Long campusId);
    List<Campus> findAll();
    Long count();
}
