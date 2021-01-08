package com.bopuniv.server.services;

import com.bopuniv.server.dto.CampusDto;
import com.bopuniv.server.entities.Campus;
import com.bopuniv.server.entities.Department;
import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.entities.User;
import com.bopuniv.server.exceptions.CampusException;
import com.bopuniv.server.exceptions.PTCException;
import com.bopuniv.server.repository.CampusRepository;
import com.bopuniv.server.repository.PTCRepository;
import jdk.jfr.TransitionTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampusService implements ICampusService {

    @Autowired
    CampusRepository repository;

    @Autowired
    PTCRepository ptcRepository;

    @Override
    public CampusDto save(CampusDto campusDto) {

        Optional<PTC> ptc = ptcRepository.findById(campusDto.getPtcId());
        if(ptc.isEmpty())
            throw new PTCException();

        Campus campus = new Campus();
        campus.setCity(campusDto.getCity());
        campus.setCountry(campusDto.getCountry());
        campus.setDescription(campusDto.getDescription());
        campus.setLatitude(campusDto.getLatitude());
        campus.setLongitude(campusDto.getLongitude());
        campus.setMain(campusDto.isMain());
        campus.setName(campusDto.getName());
        campus.setPhoneNumber(campusDto.getPhoneNumber());
        campus.setPtc(ptc.get());
        repository.save(campus);
        return new CampusDto(campus);
    }

    @Override
    public CampusDto update(CampusDto campusDto){
        Optional<PTC> ptc = ptcRepository.findById(campusDto.getPtcId());

        if(ptc.isEmpty())
            throw new PTCException();

        Optional<Campus> campusOptional = repository.findById(campusDto.getCampusId());

        if(campusOptional.isEmpty())
            throw new CampusException();

        Campus campus = campusOptional.get();
        campus.setCity(campusDto.getCity());
        campus.setCountry(campusDto.getCountry());
        campus.setDescription(campusDto.getDescription());
        campus.setLatitude(campusDto.getLatitude());
        campus.setLongitude(campusDto.getLongitude());
        campus.setMain(campusDto.isMain());
        campus.setName(campusDto.getName());
        campus.setPhoneNumber(campusDto.getPhoneNumber());

        repository.save(campus);

        return new CampusDto(campus);
    }

    @Override
    public Campus findByCampusIdAndPtc(Long campusId, PTC ptc){
        Optional<Campus> campusOptional = repository.findByCampusIdAndPtc(campusId, ptc);

        if(campusOptional.isEmpty())
            throw new CampusException();

        return campusOptional.get();
    }

    @Override
    public Campus findByCampusIdAndPtcId(Long campusId, Long ptcId){
        Optional<Campus> campusOptional = repository.findByCampusIdAndPtcPtcId(campusId, ptcId);

        if(campusOptional.isEmpty())
            throw new CampusException();

        return campusOptional.get();
    }

    @Override
    public List<Campus> findAllByPtcId(Long ptcId){
        return repository.findAllByPtcPtcId(ptcId);
    }

    @Override
    public List<Campus> findAllByPtc(PTC ptc){
        return repository.findAllByPtc(ptc);
    }

    @Override
    public Campus findById(Long id) {
        Optional<Campus> campusOptional = repository.findById(id);
        if(campusOptional.isEmpty())
            throw new CampusException();

        return campusOptional.get();
    }

    @Override
    public void delete(Long campusId) {
        repository.deleteById(campusId);
    }

    @Override
    public List<Campus> findAll() {
        return repository.findAll();
    }

    @Override
    public Long count(){
        return repository.count();
    }
}
