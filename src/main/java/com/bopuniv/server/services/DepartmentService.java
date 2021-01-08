package com.bopuniv.server.services;

import com.bopuniv.server.dto.DepartmentDto;
import com.bopuniv.server.entities.Campus;
import com.bopuniv.server.entities.CampusDept;
import com.bopuniv.server.entities.Department;
import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.exceptions.DepartmentException;
import com.bopuniv.server.exceptions.PTCException;
import com.bopuniv.server.repository.CampusDeptRepository;
import com.bopuniv.server.repository.CampusRepository;
import com.bopuniv.server.repository.DepartmentRepository;
import com.bopuniv.server.repository.PTCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional
public class DepartmentService implements IDepartmentService {
    @Autowired
    DepartmentRepository repository;

    @Autowired
    PTCRepository ptcRepository;

    @Autowired
    CampusDeptRepository campusDeptRepository;

    @Autowired
    CampusRepository campusRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public Department save(DepartmentDto departmentDto) {

        Optional<PTC> optionalPTC = ptcRepository.findById( departmentDto.getPtcId() );
        if(optionalPTC.isEmpty())
            throw new PTCException();

        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());
        department.setPtc(optionalPTC.get());
        department.setPublished(departmentDto.isPublished());
        LOGGER.debug(department.toString());
        repository.save(department);

        if(departmentDto.getCampusIds() != null && departmentDto.getCampusIds().size() > 0){
            ExecutorService service = null;
            try{
                service = Executors.newSingleThreadExecutor();
                service.execute(() ->
                        departmentDto.getCampusIds()
                                .forEach(id -> saveDeptCampus(id, department))
                );
            }finally {
                if(service != null) service.shutdown();
            }
        }

        return department;
    }

    private void saveDeptCampus(Long id, Department department){
        Optional<Campus> campusOptional = campusRepository.findById(id);
        if(campusOptional.isPresent()){
            CampusDept campusDept = new CampusDept();
            campusDept.setCampus(campusDept.getCampus());
            campusDept.setDepartment(department);
            campusDeptRepository.save(campusDept);
        }
    }

    @Override
    public Department update(DepartmentDto departmentDto){
        Optional<PTC> optionalPTC = ptcRepository.findById( departmentDto.getPtcId() );
        if(optionalPTC.isEmpty())
            throw new PTCException();

        Optional<Department> departmentOptional = repository.findById(departmentDto.getDeptId());
        if(departmentOptional.isEmpty())
            throw new DepartmentException();

        Department department = departmentOptional.get();
        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());
        department.setPublished(departmentDto.isPublished());
        repository.save(department);

        if(departmentDto.getCampusIds() !=null && departmentDto.getCampusIds().size() > 0){
            ExecutorService service = null;
            try{
                service = Executors.newSingleThreadExecutor();
                service.execute(() ->
                        departmentDto.getCampusIds()
                                .forEach(id -> saveDeptCampus(id, department))
                );
            }finally {
                if(service != null) service.shutdown();
            }
        }

        return department;
    }

    @Override
    public Department findById(Long id) {
        Optional<Department> optionalDepartment = repository.findById(id);
        if(optionalDepartment.isEmpty())
            throw new DepartmentException();
        return optionalDepartment.get();
    }

    @Override
    public void delete(Long deptId) {
        repository.deleteById(deptId);
    }

    @Override
    public void deleteByDeptIdsAndPtcId(List<Long> deptIds, Long ptcId){
        repository.deleteByDeptIdInAndPtcPtcId(deptIds, ptcId);
    }

    @Override
    public List<Department> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Department> findAllByPtcId(Long ptcId){
        return repository.findAllByPtcPtcId(ptcId);
    }

    @Override
    public List<Department> searchByPtcIdPaging(Long ptcId, Long limit, Long offset) {
        Pageable page = PageRequest.of(offset.intValue(), limit.intValue());
        return repository.findAllByPtcPtcId(ptcId, page);
    }

    @Override
    public Department findByDeptIdAndPtcId(Long deptId, Long ptcId){
        Optional<Department> departmentOptional = repository.findByDeptIdAndPtcPtcId(deptId, ptcId);
        if(departmentOptional.isEmpty())
            throw new DepartmentException();

        return departmentOptional.get();
    }

    @Override
    public long countAllByPtcId(Long ptcId) {
        return repository.countByPtcPtcId(ptcId);
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
