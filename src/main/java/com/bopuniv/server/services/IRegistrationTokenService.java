package com.bopuniv.server.services;

import com.bopuniv.server.entities.RegistrationToken;

public interface IRegistrationTokenService {
    RegistrationToken findByToken(String token);
    void save(RegistrationToken reg);
}
