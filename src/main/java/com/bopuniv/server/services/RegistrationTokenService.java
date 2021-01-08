package com.bopuniv.server.services;

import com.bopuniv.server.entities.RegistrationToken;
import com.bopuniv.server.repository.RegistrationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationTokenService implements IRegistrationTokenService{

    @Autowired
    RegistrationTokenRepository repository;

    @Override
    public RegistrationToken findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public void save(RegistrationToken reg) {
        repository.save(reg);
    }
}
