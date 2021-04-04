package com.bopuniv.server.repository;


import com.bopuniv.server.entities.Campus;
import com.bopuniv.server.entities.Department;
import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PTCRepository extends JpaRepository<PTC, Long> {

    PTC findByLongName(String name);
    PTC findByEmail(String email);

//    @Query("SELECT p FROM PTC p INNER JOIN p.campuses c WHERE c.campusId=:campusId ")
//    PTC findByCampusId(@Param("campusId") Long campusId);

//    Optional<PTC> findByPtcIdAndUsers(Long ptcId, User user);
//    Optional<PTC> findByPtcIdAndDepartmentsAndUsers(Long ptcId, Department department, User user);
//    Optional<PTC> findByDepartmentsTrainingTrainingId(Long trainingId);
}
