package com.bopuniv.server.website;

import com.bopuniv.server.dto.*;
import com.bopuniv.server.entities.*;
import com.bopuniv.server.exceptions.IllegalUserAccess;
import com.bopuniv.server.services.*;
import com.bopuniv.server.website.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.awt.print.PrinterGraphics;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/management")
class SessionManagement{

    /**
     * The admin frontend is served on /management endpoint. The web server is in charge of serving that page.
     * This endpoint exist here so a 404 is not return while working on development
     * @return
     */
    @GetMapping("")
    public String homeMangement(){
        return "Home management";
    }

    /**
     *
     * @param principal
     * @return @UserSession
     * Retrieve the user session
     */
    @GetMapping("/_session")
    public UserSession session(Principal principal){
        return new UserSession(principal.getName());
    }
}

/**
 *
 */
@RestController
@RequestMapping("/management/api/v1")
public class ManagementController {

    @Autowired
    IPtcService ptcService;

    @Autowired
    ICampusService campusService;

    @Autowired
    IDepartmentService departmentService;

    @Autowired
    ITrainingService trainingService;

    @Autowired
    IUserService userService;

    @Autowired
    IEntranceService entranceService;

    @Autowired
    ICampusDeptService campusDeptService;

    @Autowired
    ITrainingCareerService trainingCareerService;

    @Autowired
    ICareerService careerService;

    @Autowired
    IStorageService storageService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping("")
    public String adminPage(Principal principal){
        System.out.println("Admin controller for : "+principal.getName());
        return "admin";
    }

    @PostMapping("/files")
    public FileUpload handleFileUpload(@RequestParam("file") MultipartFile file) {

        String destFileName = storageService.store(file);
        return new FileUpload(file.getOriginalFilename(), destFileName, file.getSize());
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /**
     * Update PTC
     * PTC: Professional training center
     * @param ptcDto
     * @param ptcId
     * @param principal
     * @return
     */
    @PutMapping("/ptc/{ptcId}")
    public PTCDto newPTC(@RequestBody @Valid PTCDto ptcDto, @PathVariable Long ptcId, Principal principal){

        LOGGER.info("Updating a PTC");
        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId) || !user.getPtc().getPtcId().equals(ptcDto.getPtcId()))
            throw new IllegalUserAccess();

        ptcService.update(ptcDto, user);
        return ptcDto;
    }

    /**
     * @param ptcId
     * @param principal
     * @return
     */
    @GetMapping("/ptc/{ptcId}")
    public PTCDto getPTC(@PathVariable Long ptcId, Principal principal){
        User user = userService.findUserByEmail(principal.getName());

        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        return new PTCDto(user.getPtc());
    }

    @GetMapping("/ptc")
    public PTCDto getPTC(Principal principal){
        User user = userService.findUserByEmail(principal.getName());
        return new PTCDto(user.getPtc());
    }

    /**
     *
     * @param deptDto
     * @param principal
     * @return
     */
    @PostMapping("/ptc/{ptcId}/departments")
    public DepartmentDto newDept(@RequestBody @Valid DepartmentDto deptDto, @PathVariable Long ptcId, Principal principal){
        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        System.out.println(deptDto);
        deptDto.setPtcId(user.getPtc().getPtcId());
        return new DepartmentDto(departmentService.save(deptDto));
    }

    @GetMapping("/ptc/{ptcId}/departments")
    public List<DepartmentDto> getAllDepartments(@PathVariable Long ptcId,
                                                 Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        System.out.println("inside not paging department");

        List<Department> departments = departmentService.findAllByPtcId(user.getPtc().getPtcId());

        return departments.stream()
                .map(DepartmentDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/ptc/{ptcId}/departments/search")
    public SearchResult<DepartmentDto> searchDepartmentsPaging( @PathVariable Long ptcId,
                                                       @RequestParam Long limit,
                                                       @RequestParam Long offset,
                                                       @RequestParam(defaultValue = "-1") Long campusId,
                                                       Principal principal){
        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        List<Department> departments;
        long count;
        if(campusId.equals(-1L)){
            // we want to return all the department independently of the campus
            departments = departmentService.searchByPtcIdPaging(ptcId, limit, offset);
            count = departmentService.countAllByPtcId(ptcId);
        } else {
            departments = campusDeptService.searchDeptByCampusIdPaging(campusId, limit, offset);
            count = campusDeptService.countAllDeptByCampusId(campusId);
        }

        List<DepartmentDto> deptDto = departments.stream()
                .map(DepartmentDto::new)
                .collect(Collectors.toList());

        return new SearchResult<>(offset.intValue(), limit.intValue(), count, deptDto);
    }

    @GetMapping("ptc/{ptcId}/departments/{deptId}")
    public DepartmentDto getDepartment(@PathVariable Long ptcId,
                                       @PathVariable Long deptId,
                                       Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findByDeptIdAndPtcId(deptId, ptcId);

        return new DepartmentDto(department);
    }

    @PutMapping("ptc/{ptcId}/departments/{deptId}")
    public DepartmentDto updateDepartment(@RequestBody @Valid DepartmentDto departmentDto,
                                          @PathVariable Long ptcId,
                                          @PathVariable Long deptId,
                                          Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        if(!deptId.equals(departmentDto.getDeptId()))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getDeptId().equals(deptId))
            throw new IllegalUserAccess();

        return new DepartmentDto(departmentService.update(departmentDto));
    }

    @DeleteMapping("ptc/{ptcId}/departments")
    public void deleteDepartments(@RequestParam String ids, // comma separated list of ids 4,6,8
                                  @PathVariable Long ptcId,
                                  Principal principal){

        System.out.println("Deleting department: "+ids);

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        List<String> idList = Arrays.asList(ids.split(","));

        List<Long> deptIds = idList.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

//        System.out.println(deptIds);

        departmentService.deleteByDeptIdsAndPtcId(deptIds, ptcId);

    }

    @DeleteMapping("ptc/{ptcId}/departments/{deptId}")
    public DepartmentDto deleteDepartment(@RequestBody @Valid DepartmentDto departmentDto,
                                          @PathVariable Long ptcId,
                                          @PathVariable Long deptId,
                                          Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        if(!deptId.equals(departmentDto.getDeptId()))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getDeptId().equals(deptId))
            throw new IllegalUserAccess();

        departmentService.delete(deptId);
        return new DepartmentDto(department);
    }

    @GetMapping("/ptc/{ptcId}/campuses")
    public List<CampusDto> getAllCampuses(@PathVariable Long ptcId, Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        List<Campus> campuses = campusService.findAllByPtcId(user.getPtc().getPtcId());

        return campuses.stream()
                .map(CampusDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Create new Campus
     * @param campusDto
     * @param principal
     * @return
     */
    @PostMapping("/ptc/{ptcId}/campuses")
    public CampusDto newCampus(@RequestBody @Valid CampusDto campusDto,
                               @PathVariable Long ptcId,
                               Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        campusDto.setPtcId(user.getPtc().getPtcId());

        return campusService.save(campusDto);
    }

    @GetMapping("/ptc/{ptcId}/campuses/{campusId}")
    public CampusDto getCampus(@PathVariable Long ptcId,
                               @PathVariable Long campusId,
                               Principal principal) {

        User user = userService.findUserByEmail(principal.getName());

        if (!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Campus campus = campusService.findByCampusIdAndPtcId(campusId, ptcId);

        return new CampusDto(campus);
    }

    @PutMapping("/ptc/{ptcId}/campuses/{campusId}")
    public CampusDto updateCampus(@RequestBody @Valid CampusDto campusDto,
                                  @PathVariable Long ptcId,
                                  @PathVariable Long campusId,
                                  Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        if(!campusId.equals(campusDto.getCampusId()))
            throw new IllegalUserAccess();

        Campus campus = campusService.findById(campusId);

        if(campus.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        return campusService.update(campusDto);
    }

    @DeleteMapping("/ptc/{ptcId}/campuses/{campusId}")
    public void deleteCampus(@RequestBody @Valid CampusDto campusDto,
                             @PathVariable Long ptcId,
                             @PathVariable Long campusId,
                             Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        if(!campusId.equals(campusDto.getCampusId()))
            throw new IllegalUserAccess();

        Campus campus = campusService.findById(campusId);

        if(campus.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        campusService.delete(campusId);
    }

    @GetMapping("/ptc/{ptcId}/trainings")
    public SearchResult<TrainingDto> getAllTrainingByPtcPaging(@PathVariable Long ptcId,
                                                            @RequestParam Long limit,
                                                            @RequestParam Long offset,
                                                            Principal principal){
        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        long total = trainingService.countByPtcId(ptcId);
        List<Training> trainings = trainingService.searchByPtcIdPaging(ptcId, limit, offset);

        List<TrainingDto> trainingsDto = trainings.stream().map(TrainingDto::new).collect(Collectors.toList());

        return new SearchResult<>(offset.intValue(), limit.intValue(), total, trainingsDto);
    }


    @GetMapping("/ptc/{ptcId}/trainings/search")
    public SearchResult<TrainingDto> searchTrainings(@PathVariable Long ptcId,
                                                  @RequestParam Long limit,
                                                  @RequestParam Long offset,
                                                  @RequestParam(defaultValue = "-1") Long campusId,
                                                  @RequestParam(defaultValue = "-1") Long deptId,
                                                  @RequestParam(defaultValue = "") String degree,
                                                  Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        List<Training> trainings = trainingService
                .searchByDegreeAndDepartmentIdAndCampusIdAndPtcIdPaging(degree, deptId, campusId, ptcId, limit, offset);

        List<TrainingDto> trainingsDto = trainings.stream().map(TrainingDto::new).collect(Collectors.toList());

        long total = trainingService.countByDegreeAndDepartmentIdAndCampusIdAndPtcIdPaging(degree, deptId, campusId, ptcId);

        return new SearchResult<>(offset.intValue(), limit.intValue(), total, trainingsDto);
    }

    @DeleteMapping("/ptc/{ptcId}/trainings")
    public void deleteTrainings(@PathVariable Long ptcId,
                                @RequestParam String ids, // comma separated list of ids
                                Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        List<String> idList = Arrays.asList(ids.split(","));

        List<Long> trainingId = idList.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        trainingService.deleteByTrainingsAndPtc(trainingId, ptcId);
    }

    @DeleteMapping("/ptc/{ptcId}/trainings/{trainingId}")
    public void deleteATraining(@PathVariable Long ptcId,
                                @PathVariable Long trainingId, // comma separated list of ids
                                Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        trainingService.deleteByTrainingIdAndPtcId(trainingId, ptcId);
    }

    @GetMapping("/ptc/{ptcId}/departments/{deptId}/trainings")
    public List<TrainingDto> getAllTrainingByDepartment(@PathVariable Long ptcId,
                                            @PathVariable Long deptId,
                                            Principal principal){
        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        List<Training> trainings = trainingService.findAllByDeptId(deptId);

        return trainings.stream()
                        .map(TrainingDto::new).collect(Collectors.toList());
    }

    @PostMapping("/ptc/{ptcId}/departments/{deptId}/trainings")
    public TrainingDto newTraining(@RequestBody @Valid TrainingDto trainingDto,
                                   @PathVariable Long ptcId,
                                   @PathVariable Long deptId,
                                   Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(department == null ||  !department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        trainingDto.setDeptId(deptId);
        return trainingService.save(trainingDto);
    }

    @GetMapping("/ptc/{ptcId}/departments/{deptId}/trainings/{trainingId}")
    public TrainingDto getTraining(@PathVariable Long ptcId,
                                   @PathVariable Long deptId,
                                   @PathVariable Long trainingId,
                                   Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Training training = trainingService.findByTrainingIdAndDeptId(trainingId, deptId);
        return new TrainingDto(training);
    }

    @PutMapping("/ptc/{ptcId}/departments/{deptId}/trainings/{trainingId}")
    public TrainingDto updateTraining(@RequestBody @Valid TrainingDto trainingDto,
                                      @PathVariable Long ptcId,
                                      @PathVariable Long deptId,
                                      @PathVariable Long trainingId,
                                      Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        if(!trainingId.equals(trainingDto.getTrainingId()))
            throw new IllegalUserAccess();

        System.out.println(trainingDto);

        return trainingService.update(trainingDto, deptId);
    }

    @DeleteMapping("/ptc/{ptcId}/departments/{deptId}/trainings/{trainingId}")
    public void deleteTraining(@RequestBody @Valid TrainingDto trainingDto,
                               @PathVariable Long ptcId,
                               @PathVariable Long deptId,
                               @PathVariable Long trainingId,
                               Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        // Is the user accessing a department he is allowed to access?
        if(!department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        if(!trainingId.equals(trainingDto.getTrainingId()))
            throw new IllegalUserAccess();

        Training training = trainingService.findById(trainingId);

        // Check the training belongs to the department the user claim it to be
        if(!training.getDepartment().getDeptId().equals(deptId))
            throw new IllegalUserAccess();

        trainingService.delete(trainingId);

    }

    @GetMapping("/ptc/{ptcId}/departments/{deptId}/trainings/{trainingId}/entrances")
    public List<EntranceDto> getAllEntrances(@PathVariable Long ptcId,
                                             @PathVariable Long deptId,
                                             @PathVariable Long trainingId,
                                             Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(department == null || !department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Training training = trainingService.findById(trainingId);

        if(!trainingId.equals(training.getTrainingId()))
            throw new IllegalUserAccess();

        List<Entrance> entrances = entranceService.findAllByTraining(training);

        return entrances.stream()
                .map(EntranceDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/ptc/{ptcId}/departments/{deptId}/campuses")
    public List<CampusDto> getCampusesForDept(@PathVariable Long ptcId,
                                           @PathVariable Long deptId,
                                           Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(department == null || !department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        return campusDeptService.findAllCampusesOfDept(deptId)
                .stream()
                .map(CampusDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/ptc/{ptcId}/campuses/{campusId}/departments")
    public List<DepartmentDto> getDepartmentsOfCampus(@PathVariable Long ptcId,
                                                      @PathVariable Long campusId,
                                                      Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Campus campus = campusService.findById(campusId);

        if(campus.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        return campusDeptService.findAllDeptByCampusId(campusId)
                .stream()
                .map(DepartmentDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/ptc/{ptcId}/departments/{deptId}/campuses/{campusId}")
    public void setCampusDept(@PathVariable Long ptcId,
                              @PathVariable Long deptId,
                              @PathVariable Long campusId,
                              Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        campusDeptService.saveCampusDept(campusId, deptId);

    }

    @DeleteMapping("/ptc/{ptcId}/departments/{deptId}/campuses/{campusId}")
    public void deleteCampusDept(@PathVariable Long ptcId,
                                 @PathVariable Long deptId,
                                 @PathVariable Long campusId,
                                 Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        campusDeptService.deleteCampusDept(campusId, deptId);

    }

    @GetMapping("/ptc/{ptcId}/departments/{deptId}/trainings/{trainingId}/careers")
    public List<CareerDto> getAllCareerForTraining(@PathVariable Long ptcId,
                                                   @PathVariable Long deptId,
                                                   @PathVariable Long trainingId,
                                                   Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        return trainingCareerService.findAllByTrainingId(trainingId)
                .stream()
                .map(CareerDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/ptc/{ptcId}/departments/{deptId}/trainings/{trainingId}/careers/{careerId}")
    public GenericResponse createTrainingCareer(@PathVariable Long ptcId,
                                     @PathVariable Long deptId,
                                     @PathVariable Long trainingId,
                                     @PathVariable Long careerId,
                                     Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        trainingCareerService.saveTrainingCareer(trainingId, careerId, deptId);

        return new GenericResponse("Career for training created"); // might need to set the proper http status to 201

    }

    @DeleteMapping("/ptc/{ptcId}/departments/{deptId}/trainings/{trainingId}/careers/{careerId}")
    public void deleteTrainingCareer(@PathVariable Long ptcId,
                                     @PathVariable Long deptId,
                                     @PathVariable Long trainingId,
                                     @PathVariable Long careerId,
                                     Principal principal){

        User user = userService.findUserByEmail(principal.getName());
        if(!user.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        Department department = departmentService.findById(deptId);

        if(!department.getPtc().getPtcId().equals(ptcId))
            throw new IllegalUserAccess();

        trainingCareerService.deleteTrainingCareer(trainingId, careerId, deptId);

    }

    @GetMapping("/careers")
    public List<CareerDto> getAllCareers(){

        return careerService.findAll()
                .stream()
                .map(CareerDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/careers")
    public List<CareerDto> createCareers(@RequestBody List<CareerDto> careersDto){

        return careersDto;
    }

}
