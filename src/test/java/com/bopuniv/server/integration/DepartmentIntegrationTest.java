package com.bopuniv.server.integration;

import com.bopuniv.server.entities.Department;
import com.bopuniv.server.entities.PTC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.bopuniv.server.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
public class DepartmentIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DepartmentRepository repository;

    private Department dept1;
    private Department dept2;
    private Department dept3;
    private Department dept4;
    private PTC ptc1;
    private PTC ptc2;

    @Before
    public void before() {
        ptc1 = new PTC("Testing PTC 1", "email@email.com", "test description");
        ptc2 = new PTC("Testing PTC 2", "email2@email.com", "test description 2");
        dept1 = new Department("Department 1", "description of department 1", ptc1);
        dept2 = new Department("Department 2", "description of department 2", ptc1);
        dept3 = new Department("Department 3", "description of department 3", ptc1);
        dept4 = new Department("Department 4", "description of department 4", ptc2);
        entityManager.persist(ptc1);
        entityManager.persist(ptc2);
        entityManager.persist(dept1);
        entityManager.persist(dept2);
        entityManager.persist(dept3);
        entityManager.persist(dept4);
    }

    @Test
    public void givenQueryMethod_whenPtcIdIsNotNull_thenReturnAllDepartments() {
        List<Department> departments = repository.findAllByPtcPtcId(ptc1.getPtcId());
        assertEquals(3, departments.size());
    }

    @Test
    public void whenDeleteDepartmentsGivenListOfDept_repositoryShouldNotHaveThoseDepartments(){
        List<Long> deptIds = Arrays.asList(dept1.getDeptId(), dept2.getDeptId());
        List<Department> departments = repository.findAllById(deptIds);
        repository.deleteAll(departments);
        List<Department> depts = repository.findAll();
        assertEquals(2, depts.size());
    }

    @Test
    public void whenDeleteDepartmentsGivenListOfDeptId_repositoryShouldNotHaveThoseDepartments(){
        List<Long> deptIds = Arrays.asList(dept1.getDeptId(), dept2.getDeptId());
        repository.deleteByDeptIdIn(deptIds);
        List<Department> depts = repository.findAllByPtcPtcId(1L);
        assertEquals(1, depts.size());
    }

    @Test
    public void whenDeleteByIdAndPtcId_departmentsForThatPtcShouldBe2(){
        repository.deleteByDeptIdAndPtcPtcId(dept1.getDeptId(), ptc1.getPtcId());
        List<Department> departments = repository.findAllByPtcPtcId(ptc1.getPtcId());
        assertEquals(2, departments.size());
    }

    @Test
    public void whenDeleteByPtcIdAndAGivenListOfDeptIds_departmentsForThatPtcShouldBe1(){
        List<Long> deptIds = Arrays.asList(dept1.getDeptId(), dept2.getDeptId());
        repository.deleteByDeptIdInAndPtcPtcId(deptIds, ptc1.getPtcId());
        List<Department> departments = repository.findAllByPtcPtcId(ptc1.getPtcId());
        assertEquals(1, departments.size());
    }

    @Test
    public void whenDeleteAllForAPtcId_departmentsForThatPtcShouldBeEmpty(){
        Long ptcId = 1L;
        repository.deleteByPtcPtcId(ptcId);
        List<Department> departments = repository.findAllByPtcPtcId(ptcId);
        assertEquals(0, departments.size());
    }

    @After
    public void cleanUp() {
        repository.deleteAll();
    }
}