package com.bopuniv.server.services;

import com.bopuniv.server.dto.TrainingDto;
import com.bopuniv.server.entities.*;
import com.bopuniv.server.exceptions.TrainingException;
import com.bopuniv.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingService implements ITrainingService {

    @Autowired
    TrainingRepository repository;

    @Autowired
    DepartmentRepository deptRepository;

    @Autowired
    CareerRepository careerRepository;

    @Autowired
    TrainingCareerRepository trainingCareerRepository;

    @Autowired
    CampusDeptRepository campusDeptRepository;

    @Override
    public TrainingDto save(TrainingDto trainingDto) {

        Optional<Department> departmentOptional = deptRepository.findById(trainingDto.getDeptId());

        if(departmentOptional.isEmpty())
            throw new TrainingException();

        Training training = new Training();
        training.setCost(trainingDto.getCost());
        training.setDegree(trainingDto.getDegree());
        training.setDepartment(departmentOptional.get());
        training.setDescription(trainingDto.getDescription());
        training.setDuration(trainingDto.getDuration());
        training.setAdmissionOn(trainingDto.getAdmissionOn());
        training.setPublished(trainingDto.isPublished());
        training.setName(trainingDto.getName());
        training.setPreRequisite(trainingDto.getPreRequisite());
        training.setActivityDomain(trainingDto.getActivityDomain());

        repository.save(training);

        if(trainingDto.getCareerIds() != null && trainingDto.getCareerIds().size() > 0 ){
            ExecutorService service = null;
            try{
                service = Executors.newSingleThreadExecutor();
                service.execute(() ->
                        trainingDto.getCareerIds()
                                .parallelStream()
                                .forEach(id -> setTrainingCareer(id, training))
                );
            }finally {
                if(service != null) service.shutdown();
            }
        }

        return new TrainingDto(training);

    }

    private void setTrainingCareer(Long careerId, Training training){
        Optional<Career> career = careerRepository.findById(careerId);
        if(career.isPresent()) {
            TrainingCareer trainingCareer = new TrainingCareer();
            trainingCareer.setTraining(training);
            trainingCareer.setCareer(career.get());
            trainingCareerRepository.save(trainingCareer);
        }
    }

    @Override
    public TrainingDto update(TrainingDto trainingDto, Long deptId){

        Optional<Training> trainingOptional = repository.findByTrainingIdAndDepartmentDeptId(trainingDto.getTrainingId(), deptId);

        if(trainingOptional.isEmpty())
            throw new TrainingException();

        Training training = trainingOptional.get();
        training.setCost(trainingDto.getCost());
        training.setDegree(trainingDto.getDegree());
        training.setDescription(trainingDto.getDescription());
        training.setDuration(trainingDto.getDuration());
        training.setAdmissionOn(trainingDto.getAdmissionOn());
        training.setPublished(trainingDto.isPublished());
        training.setName(trainingDto.getName());
        training.setPreRequisite(trainingDto.getPreRequisite());
        training.setActivityDomain(trainingDto.getActivityDomain());

        repository.save(training);

        if(trainingDto.getCareerIds() != null && trainingDto.getCareerIds().size() > 0 ){
            ExecutorService service = null;
            try{
                service = Executors.newSingleThreadExecutor();
                service.execute(() ->
                        trainingDto.getCareerIds()
                                .parallelStream()
                                .forEach(id -> setTrainingCareer(id, training))
                );
            }finally {
                if(service != null) service.shutdown();
            }
        }

        return new TrainingDto(training);
    }

    @Override
    public Training findById(Long id) {
        Optional<Training> trainingOptional = repository.findById(id);
        if(trainingOptional.isEmpty())
            throw new TrainingException();
        return trainingOptional.get();
    }

    @Override
    public Training findByTrainingIdAndDeptId(Long trainingId, Long deptId){
        Optional<Training> trainingOptional = repository.findByTrainingIdAndDepartmentDeptId(trainingId, deptId);
        if(trainingOptional.isEmpty())
            throw new TrainingException();

        return trainingOptional.get();
    }

    @Override
    public List<Training> findAllByDeptId(Long deptId){
        return repository.findAllByDepartmentDeptId(deptId);
    }

    @Override
    public List<Training> searchByDeptIdPaging(Long deptId, Long limit, Long offset) {
        Pageable page = PageRequest.of(offset.intValue(), limit.intValue());
        return repository.findAllByDepartmentDeptId(deptId, page);
    }

    @Override
    public List<Training> searchByPtcIdPaging(Long ptcId, Long limit, Long offset) {
        Pageable page = PageRequest.of(offset.intValue(), limit.intValue());
        return repository.findAllByDepartmentPtcPtcId(ptcId, page);
    }

    @Override
    public List<Training> searchByDegreeAndDepartmentIdAndCampusIdAndPtcIdPaging(String degree,
                                                                                 Long deptId,
                                                                                 Long campusId,
                                                                                 Long ptcId,
                                                                                 Long limit,
                                                                                 Long offset){
        Pageable page = PageRequest.of(offset.intValue(), limit.intValue());
        List<Training> trainings = new ArrayList<>();
        if(campusId.equals(-1L) && deptId.equals(-1L) && "".equals(degree.trim())){
            // none of the values are set
            trainings = repository.findAllByDepartmentPtcPtcId(ptcId, page);
        } else if(!campusId.equals(-1L) && !deptId.equals(-1L) && !"".equals(degree.trim())){
            // all of the values are set
            Optional<CampusDept> campusDeptOptional = campusDeptRepository.findByCampusCampusIdAndDepartmentDeptId(campusId, deptId);
            if(campusDeptOptional.isPresent()) {
                Department department = campusDeptOptional.get().getDepartment();
                List<Department> departments = Collections.singletonList(department);
                trainings = repository.findAllByDegreeContainingAndDepartmentInAndDepartmentPtcPtcId(degree, departments, ptcId, page);
            }
        } else if(!campusId.equals(-1L) && deptId.equals(-1L) && "".equals(degree.trim())){
            // only campus is set
            List<CampusDept> deptCampus = campusDeptRepository.findAllByCampusCampusId(campusId);
            List<Department> departments = deptCampus
                    .stream()
                    .map(CampusDept::getDepartment)
                    .collect(Collectors.toList());
            trainings = repository.findAllByDepartmentInAndDepartmentPtcPtcId(departments, ptcId, page);
        }else if(campusId.equals(-1L) && !deptId.equals(-1L) && "".equals(degree.trim())){
            // only dept is set
            Optional<Department> departmentOptional = deptRepository.findByDeptIdAndPtcPtcId(deptId, ptcId);
            if(departmentOptional.isPresent()) {
                List<Department> departments = Collections.singletonList(departmentOptional.get());
                trainings = repository.findAllByDepartmentInAndDepartmentPtcPtcId(departments, ptcId, page);
            }
        } else if (campusId.equals(-1L) && deptId.equals(-1L) && !"".equals(degree.trim())){
            // only degree is set
            trainings = repository.findAllByDegreeContainingAndDepartmentPtcPtcId(degree, ptcId, page);
        } else if(!campusId.equals(-1L) && !deptId.equals(-1L) && "".equals(degree.trim())){
            // campus and dept are set
            Optional<CampusDept> campusDeptOptional = campusDeptRepository.findByCampusCampusIdAndDepartmentDeptId(campusId, deptId);
            if(campusDeptOptional.isPresent()) {
                Department department = campusDeptOptional.get().getDepartment();
                List<Department> departments = Collections.singletonList(department);
                trainings = repository.findAllByDepartmentInAndDepartmentPtcPtcId(departments, ptcId, page);
            }
        } else if(!campusId.equals(-1L) && deptId.equals(-1L) && !"".equals(degree.trim())){
            // campus and degree are set
            List<CampusDept> deptCampus = campusDeptRepository.findAllByCampusCampusId(campusId);
            List<Department> departments = deptCampus
                    .stream()
                    .map(CampusDept::getDepartment)
                    .collect(Collectors.toList());
            trainings = repository.findAllByDegreeContainingAndDepartmentInAndDepartmentPtcPtcId(degree, departments, ptcId, page);

        } else if (campusId.equals(-1L) && !deptId.equals(-1L) && !"".equals(degree.trim())){
            // dept and degree set
            Optional<Department> departmentOptional = deptRepository.findByDeptIdAndPtcPtcId(deptId, ptcId);
            if(departmentOptional.isPresent()) {
                List<Department> departments = Collections.singletonList(departmentOptional.get());
                trainings = repository.findAllByDegreeContainingAndDepartmentInAndDepartmentPtcPtcId(degree, departments, ptcId, page);
            }
        }

        return trainings;
    }

    @Override
    public List<Training> searchByActivityDomainPaging(Long limit, Long offset, String activityDomain){
        Pageable page = PageRequest.of(offset.intValue(), limit.intValue());
        return repository.findAllByActivityDomainContaining(activityDomain, page);
    }

    @Override
    public List<Training> searchAllPage(Long limit, Long offset) {
        Pageable page = PageRequest.of(offset.intValue(), limit.intValue());
        Page<Training> pages = repository.findAll(page);
        return pages.get().collect(Collectors.toList());
    }

    @Override
    public Long countByDegreeAndDepartmentIdAndCampusIdAndPtcIdPaging(String degree,
                                                                      Long deptId,
                                                                      Long campusId,
                                                                      Long ptcId){

        long count = 0L;

        if(campusId.equals(-1L) && deptId.equals(-1L) && "".equals(degree.trim())){
            // none of the values are set
            count = repository.countByDepartmentPtcPtcId(ptcId);
        } else if(!campusId.equals(-1L) && !deptId.equals(-1L) && !"".equals(degree.trim())){
            // all of the values are set
            Optional<CampusDept> campusDeptOptional = campusDeptRepository.findByCampusCampusIdAndDepartmentDeptId(campusId, deptId);
            if(campusDeptOptional.isPresent()) {
                Department department = campusDeptOptional.get().getDepartment();
                List<Department> departments = Collections.singletonList(department);
                count = repository.countByDegreeContainingAndDepartmentInAndDepartmentPtcPtcId(degree, departments, ptcId);
            }
        } else if(!campusId.equals(-1L) && deptId.equals(-1L) && "".equals(degree.trim())){
            // only campus is set
            List<CampusDept> deptCampus = campusDeptRepository.findAllByCampusCampusId(campusId);
            List<Department> departments = deptCampus
                    .stream()
                    .map(CampusDept::getDepartment)
                    .collect(Collectors.toList());
            count = repository.countByDepartmentInAndDepartmentPtcPtcId(departments, ptcId);
        }else if(campusId.equals(-1L) && !deptId.equals(-1L) && "".equals(degree.trim())){
            // only dept is set
            Optional<Department> departmentOptional = deptRepository.findByDeptIdAndPtcPtcId(deptId, ptcId);
            if(departmentOptional.isPresent()) {
                List<Department> departments = Collections.singletonList(departmentOptional.get());
                count = repository.countByDepartmentInAndDepartmentPtcPtcId(departments, ptcId);
            }
        } else if (campusId.equals(-1L) && deptId.equals(-1L) && !"".equals(degree.trim())){
            // only degree is set
            count = repository.countByDegreeContainingAndDepartmentPtcPtcId(degree, ptcId);
        } else if(!campusId.equals(-1L) && !deptId.equals(-1L) && "".equals(degree.trim())){
            // campus and dept are set
            Optional<CampusDept> campusDeptOptional = campusDeptRepository.findByCampusCampusIdAndDepartmentDeptId(campusId, deptId);
            if(campusDeptOptional.isPresent()) {
                Department department = campusDeptOptional.get().getDepartment();
                List<Department> departments = Collections.singletonList(department);
                count = repository.countByDepartmentInAndDepartmentPtcPtcId(departments, ptcId);
            }
        } else if(!campusId.equals(-1L) && deptId.equals(-1L) && !"".equals(degree.trim())){
            // campus and degree are set
            List<CampusDept> deptCampus = campusDeptRepository.findAllByCampusCampusId(campusId);
            List<Department> departments = deptCampus
                    .stream()
                    .map(CampusDept::getDepartment)
                    .collect(Collectors.toList());
            count = repository.countByDegreeContainingAndDepartmentInAndDepartmentPtcPtcId(degree, departments, ptcId);

        } else if (campusId.equals(-1L) && !deptId.equals(-1L) && !"".equals(degree.trim())){
            // dept and degree set
            Optional<Department> departmentOptional = deptRepository.findByDeptIdAndPtcPtcId(deptId, ptcId);
            if(departmentOptional.isPresent()) {
                List<Department> departments = Collections.singletonList(departmentOptional.get());
                count = repository.countByDegreeContainingAndDepartmentInAndDepartmentPtcPtcId(degree, departments, ptcId);
            }
        }

        return count;
    }

    @Override
    public void delete(Long trainingId) {
        repository.deleteById(trainingId);
    }

    @Override
    public void deleteTrainings(List<Long> trainingIds) {
        repository.deleteByTrainingIdIn(trainingIds);
    }

    @Override
    public void deleteByTrainingsAndDept(List<Long> trainingIds, Long deptId) {
        repository.deleteByTrainingIdInAndDepartmentDeptId(trainingIds, deptId);
    }

    @Override
    public void deleteByTrainingsAndPtc(List<Long> trainingIds, Long ptcId) {
        repository.deleteByTrainingIdInAndDepartmentPtcPtcId(trainingIds, ptcId);
    }

    @Override
    public void deleteAllTrainingsByDepartment(Long deptId) {
        repository.deleteByDepartmentDeptId(deptId);
    }

    @Override
    public void deleteAllTrainingsByPtc(Long ptcId) {
        repository.deleteByDepartmentPtcPtcId(ptcId);
    }

    @Override
    public void deleteByTrainingIdAndPtcId(Long trainingId, Long ptcId) {
        repository.deleteByTrainingIdAndDepartmentPtcPtcId(trainingId, ptcId);
    }

    @Override
    public void deleteByTrainingIdAndDeptId(Long trainingId, Long deptId) {
        repository.deleteByTrainingIdAndDepartmentDeptId(trainingId, deptId);
    }

    @Override
    public List<Training> findAll() {
        return repository.findAll();
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Long countByPtcId(Long ptcId) {
        return repository.countByDepartmentPtcPtcId(ptcId);
    }

    @Override
    public Long countByDeptId(Long deptId) {
        return repository.countByDepartmentDeptId(deptId);
    }


}
