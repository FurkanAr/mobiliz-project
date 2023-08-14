package com.mobiliz.startup;

import com.mobiliz.request.CompanyRequest;
import com.mobiliz.service.CompanyService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class DataLoad {

    private final CompanyService companyService;

    public DataLoad(CompanyService companyService) {
        this.companyService = companyService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        CompanyRequest mobiliz = new CompanyRequest("Mobiliz");
        CompanyRequest navigator = new CompanyRequest("Navigator");
        CompanyRequest tracker = new CompanyRequest("Tracker");

        companyService.createCompany(mobiliz);
        companyService.createCompany(navigator);
        companyService.createCompany(tracker);

    }

}
