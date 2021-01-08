package com.bopuniv.server.services;

import com.bopuniv.server.dto.PTCDto;
import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.entities.User;
import com.bopuniv.server.exceptions.IllegalUserAccess;
import com.bopuniv.server.exceptions.PTCException;
import com.bopuniv.server.repository.PTCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PTCService implements IPtcService {

    @Autowired
    PTCRepository repository;

    @Override
    public void save(PTCDto ptcDto) {

        PTC ptc = new PTC();

        ptc.setCountry(ptcDto.getCountry());
        ptc.setEmail(ptcDto.getEmail());
        ptc.setTwitterHandler(ptcDto.getTwitterHandler());
        ptc.setFacebookHandler(ptcDto.getFacebookHandler());
        ptc.setInstagramHandler(ptcDto.getInstagramHandler());
        ptc.setLinkedInHandler(ptcDto.getLinkedInHandler());
        ptc.setLogoUrl(ptcDto.getLogoUrl());
        ptc.setLongName(ptcDto.getLongName());
        ptc.setPhoneNumber(ptcDto.getPhoneNumber());
        ptc.setShortName(ptcDto.getShortName());
        ptc.setWebsiteUrl(ptcDto.getWebsiteUrl());

        repository.save(ptc);
    }

    @Override
    public PTC save(String ptcName, String email){

        PTC ptc = new PTC();
        ptc.setLongName(ptcName);
        ptc.setEmail(email);
        repository.save(ptc);

        return repository.findByLongName(ptcName);
    }

    @Override
    public void update(PTCDto ptcDto, User user){
        PTC ptc = user.getPtc();

        ptc.setCountry(ptcDto.getCountry());
        ptc.setEmail(ptcDto.getEmail());
        ptc.setTwitterHandler(ptcDto.getTwitterHandler());
        ptc.setFacebookHandler(ptcDto.getFacebookHandler());
        ptc.setInstagramHandler(ptcDto.getInstagramHandler());
        ptc.setLogoUrl(ptcDto.getLogoUrl());
        ptc.setLongName(ptcDto.getLongName());
        ptc.setPhoneNumber(ptcDto.getPhoneNumber());
        ptc.setShortName(ptcDto.getShortName());
        ptc.setLinkedInHandler(ptcDto.getLinkedInHandler());
        ptc.setWebsiteUrl(ptcDto.getWebsiteUrl());
        repository.save(ptc);
    }

    @Override
    public PTC findById(Long id) {
        Optional<PTC> ptcOptional = repository.findById(id);
        if(ptcOptional.isEmpty())
            throw new PTCException();
        return ptcOptional.get();
    }

    @Override
    public List<PTC> findAll() {
        return repository.findAll();
    }
}
