package com.bopuniv.server.services;

import com.bopuniv.server.dto.PTCDto;
import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.entities.User;

import java.util.List;
import java.util.Optional;

public interface IPtcService {
    void save(PTCDto ptcDto);
    void update(PTCDto ptcDto, User user);
    PTC save(String ptcName, String email);
    PTC findById(Long id);
    List<PTC> findAll();
}
