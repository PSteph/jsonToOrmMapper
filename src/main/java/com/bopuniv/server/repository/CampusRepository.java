package com.bopuniv.server.repository;

import com.bopuniv.server.entities.Campus;
import com.bopuniv.server.entities.PTC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findByCampusIdAndPtcPtcId(Long campusId, Long ptcId);
    Optional<Campus> findByCampusIdAndPtc(Long campusId, PTC ptc);
    List<Campus> findAllByPtc(PTC ptc);
    // PtcPtcId is similar to ptc.getPtcId() so it is going to look for the ptcId property in ptc class
    List<Campus> findAllByPtcPtcId(Long ptcId);
}
