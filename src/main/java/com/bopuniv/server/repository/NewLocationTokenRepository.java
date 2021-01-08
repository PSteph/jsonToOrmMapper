package com.bopuniv.server.repository;

import com.bopuniv.server.entities.NewLocationToken;
import com.bopuniv.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(User userLocation);

}
