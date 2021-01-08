package com.bopuniv.server.services;

import com.bopuniv.server.website.util.ActivityDomainProperties;
import org.apache.tomcat.util.descriptor.LocalResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ActivitiesDomain {

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private ActivityDomainProperties activityDomainProperties;

    @Autowired
    private MessageSource messageSource;

    public List<String> getDomains(HttpServletRequest request) {
        final Locale locale = localeResolver.resolveLocale(request);
        List<String> domainTitles = activityDomainProperties.getDomains();
        List<String> domains = new ArrayList<>();

        for(String title : domainTitles)
            domains.add(messageSource.getMessage("activity.domain."+title, null, null, locale));

        return domains;
    }

}
