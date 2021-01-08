package com.bopuniv.server.repository;

import com.bopuniv.server.entities.User;
import com.bopuniv.server.entities.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    UserLocation findByCountryAndUser(String country, User user);

}
