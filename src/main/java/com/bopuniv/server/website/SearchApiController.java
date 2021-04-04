package com.bopuniv.server.website;

import com.bopuniv.server.dto.PTCDto;
import com.bopuniv.server.dto.SearchResult;
import com.bopuniv.server.dto.TrainingDto;
import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.entities.Training;
import com.bopuniv.server.services.ActivitiesDomain;
import com.bopuniv.server.services.IPtcService;
import com.bopuniv.server.services.ITrainingService;
import com.bopuniv.server.services.PTCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search/v1")
public class SearchApiController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ActivitiesDomain activitiesDomain;

    @Autowired
    private ITrainingService trainingService;

    @Autowired
    private IPtcService ptcService;

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/trainings")
    public SearchResult<TrainingDto> searchTraining(HttpServletRequest request,
                                                    @RequestParam(defaultValue = "1000") long limit,
                                                    @RequestParam(defaultValue = "0") long offset,
                                                    @RequestParam(defaultValue = "") String domain){

        List<TrainingDto> trainings;

        if(domain.trim().isEmpty())
            trainings = trainingService.searchAllPublishedPage(limit, offset)
                                            .stream()
                                            .map(t -> {
                                                System.out.println(t);
                                                TrainingDto trainingDto = new TrainingDto(t);
                                                trainingDto.setPtc(new PTCDto(t.getDepartment().getPtc()));
                                                return trainingDto;
                                            })
                                            .collect(Collectors.toList());
        else
            trainings = trainingService.searchByActivityDomainAndPublishedPaging(limit, offset, domain.trim())
                    .stream()
                    .map(t -> {
                        System.out.println(t);
                        TrainingDto trainingDto = new TrainingDto(t);
                        trainingDto.setPtc(new PTCDto(t.getDepartment().getPtc()));
                        return trainingDto;
                    })
                    .collect(Collectors.toList());

//        System.out.println(trainings);

        return new SearchResult<>(0, 0,0, trainings);
    }

    @GetMapping("/trainings/{tId}")
    public SearchResult<TrainingDto> getTraining(@PathVariable Long tId){
        Training training = trainingService.findById(tId);
        TrainingDto trainingDto = new TrainingDto(training);
        trainingDto.setPtc(new PTCDto(training.getDepartment().getPtc()));
        return new SearchResult<>(0, 0, 1, Arrays.asList(trainingDto));
    }

    @GetMapping("/domains")
    public SearchResult<String> getDomains(HttpServletRequest request){
        final Locale locale = localeResolver.resolveLocale(request);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        List<String> domains = bundle.keySet()
                .stream()
                .filter(key->key.startsWith("activity.domain"))
                .map(bundle::getString)
                .collect(Collectors.toList());

        return new SearchResult<>(0,0,0, domains);
    }

    @GetMapping("/test2")
    public List<String> getDomains2(HttpServletRequest request){
        return activitiesDomain.getDomains(request);
    }
}
