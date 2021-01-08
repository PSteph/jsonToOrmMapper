package com.bopuniv.server.integration;

import com.bopuniv.server.entities.Department;
import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.entities.Training;
import com.bopuniv.server.repository.TrainingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TrainingIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TrainingRepository repository;

    private Department dept1;
    private Department dept2;
    private PTC ptc1;
    private PTC ptc2;
    private Training tr1;
    private Training tr2;
    private Training tr3;
    private Training tr4;
    private Training tr5;


    @Before
    public void before() {
        ptc1 = new PTC("Testing PTC 1", "email@email.com");
        ptc2 = new PTC("Testing PTC 2", "email2@email.com");
        dept1 = new Department("Department 1", "description of department 1", ptc1);
        dept2 = new Department("Department 2", "description of department 2", ptc1);
        tr1 = new Training("Training 1", dept1);
        tr2 = new Training("Training 2", dept1);
        tr3 = new Training("Training 3", dept1);
        tr4 = new Training("Training 4", dept2);
        tr5 = new Training("Training 5", dept2);
        entityManager.persist(ptc1);
        entityManager.persist(ptc2);
        entityManager.persist(dept1);
        entityManager.persist(dept2);
        entityManager.persist(tr1);
        entityManager.persist(tr2);
        entityManager.persist(tr3);
        entityManager.persist(tr4);
        entityManager.persist(tr5);
    }

    @Test
    public void givenQueryMethod_whenDeptIdIsNotNull_thenReturnAllTrainingsForDept() {
        List<Training> trainings = repository.findAllByDepartmentDeptId(dept1.getDeptId());
        assertEquals(3, trainings.size());
    }

    @Test
    public void givenQueryMethod_whenPtcIdIsNotNull_thenReturnAllTrainingsForPtc() {
        List<Training> trainings = repository.findAllByDepartmentPtcPtcId(ptc1.getPtcId());
        assertEquals(5, trainings.size());
    }

    @Test
    public void givenQueryMethod_returnCountByPtc(){
        assertEquals(5, repository.countByDepartmentPtcPtcId(ptc1.getPtcId()));
    }

    @Test
    public void givenQueryMethod_returnCountByDeptId(){
        assertEquals(2, repository.countByDepartmentDeptId(dept2.getDeptId()));
    }
}
