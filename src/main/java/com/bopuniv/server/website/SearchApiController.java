package com.bopuniv.server.website;

import com.bopuniv.server.dto.SearchResult;
import com.bopuniv.server.dto.TrainingDto;
import com.bopuniv.server.services.ActivitiesDomain;
import com.bopuniv.server.services.ITrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @Autowired
    private ActivitiesDomain activitiesDomain;

    @Autowired
    private ITrainingService trainingService;

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/trainings")
    public SearchResult<TrainingDto> searchTraining(HttpServletRequest request,
                                                    @RequestParam(defaultValue = "1000") long limit,
                                                    @RequestParam(defaultValue = "0") long offset,
                                                    @RequestParam(defaultValue = "") String domain){

        List<TrainingDto> trainings;
        if(domain.isEmpty())
            trainings = trainingService.searchAllPage(limit, offset)
                                            .stream()
                                            .map(TrainingDto::new)
                                            .collect(Collectors.toList());
        else
            trainings = trainingService.searchByActivityDomainPaging(limit, offset, domain)
                    .stream()
                    .map(TrainingDto::new)
                    .collect(Collectors.toList());

        return new SearchResult<>(0, 0,0, trainings);
    }

    @GetMapping("/domains")
    public List<String> getDomains(HttpServletRequest request){
        final Locale locale = localeResolver.resolveLocale(request);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
//        LocalDate date = LocalDate.of(2020, Month.APRIL, 5);
//        System.out.println(date.getDayOfYear());
        return bundle.keySet()
                .stream()
                .filter(key->key.startsWith("activity.domain"))
                .map(bundle::getString)
                .collect(Collectors.toList());
    }

    @GetMapping("/test2")
    public List<String> getDomains2(HttpServletRequest request){
        return activitiesDomain.getDomains(request);
    }
}
