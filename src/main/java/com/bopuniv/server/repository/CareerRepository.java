package com.bopuniv.server.repository;

import com.bopuniv.server.entities.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}
