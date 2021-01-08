package com.bopuniv.server.repository;

import com.bopuniv.server.entities.RegistrationToken;
import com.bopuniv.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Integer> {
    RegistrationToken findByToken(String token);
}
