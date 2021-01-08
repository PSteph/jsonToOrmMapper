package com.bopuniv.server.services;

import com.bopuniv.server.entities.Campus;
import com.bopuniv.server.entities.CampusDept;
import com.bopuniv.server.entities.Department;
import com.bopuniv.server.exceptions.CampusException;
import com.bopuniv.server.exceptions.DepartmentException;
import com.bopuniv.server.repository.CampusDeptRepository;
import com.bopuniv.server.repository.CampusRepository;
import com.bopuniv.server.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampusDeptService implements ICampusDeptService{

    @Autowired
    CampusDeptRepository repository;

    @Autowired
    CampusRepository campusRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAllDeptByCampusId(Long campusId) {

        List<CampusDept> campusDepts = repository.findAllByCampusCampusId(campusId);

        return campusDepts.stream()
                .map(CampusDept::getDepartment)
                .collect(Collectors.toList());
    }

    @Override
    public List<Department> searchDeptByCampusIdPaging(Long campusId, Long limit, Long offset){
        Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue());
        List<CampusDept> campusDepts = repository.findAllByCampusCampusId(campusId, pageable);
        return campusDepts.stream()
                    .map(CampusDept::getDepartment)
                    .collect(Collectors.toList());
    }

    @Override
    public long countAllDeptByCampusId(Long campusId) {
        return repository.countByCampusCampusId(campusId);
    }

    @Override
    public Department findDeptByCampusIdDeptId(Long campusId, Long deptId){
        Optional<CampusDept> optionalDepartment =
                repository.findByCampusCampusIdAndDepartmentDeptId(campusId, deptId);

        if(optionalDepartment.isEmpty())
            throw new DepartmentException("Can't find matching department");
        return optionalDepartment.get().getDepartment();
    }

    @Override
    public List<Department> searchDeptByCampusIdDeptId(Long campusId, Long deptId){
        Optional<CampusDept> optionalDepartment =
                repository.findByCampusCampusIdAndDepartmentDeptId(campusId, deptId);

        List<Department> departments = new ArrayList<>();
        if(optionalDepartment.isEmpty())
            return departments;

        departments.add(optionalDepartment.get().getDepartment());
        return departments;
    }

    @Override
    public List<Campus> findAllCampusesOfDept(Long deptId) {
        List<CampusDept> campusDepts = repository.findAllByDepartmentDeptId(deptId);

        return campusDepts.stream()
                .map(CampusDept::getCampus)
                .collect(Collectors.toList());
    }

    @Override
    public void saveCampusDept(Long campusId, Long deptId) {
        Optional<Campus> campusOptional = campusRepository.findById(campusId);
        if(campusOptional.isEmpty())
            throw new CampusException();

        Optional<Department> departmentOptional = departmentRepository.findById(deptId);

        if(departmentOptional.isEmpty())
            throw new DepartmentException();

        CampusDept campusDept = new CampusDept();
        campusDept.setDepartment(departmentOptional.get());
        campusDept.setCampus(campusOptional.get());

        repository.save(campusDept);
    }

    @Override
    public void deleteCampusDept(Long campusId, Long deptId) {
        Optional<Campus> campusOptional = campusRepository.findById(campusId);
        if(campusOptional.isEmpty())
            throw new CampusException();

        Optional<Department> departmentOptional = departmentRepository.findById(deptId);

        if(departmentOptional.isEmpty())
            throw new DepartmentException();

        Optional<CampusDept> campusDeptOptional = repository.findByCampusCampusIdAndDepartmentDeptId(campusId, deptId);

        if(campusDeptOptional.isEmpty())
            throw new CampusException();

        repository.delete(campusDeptOptional.get());
    }
}
