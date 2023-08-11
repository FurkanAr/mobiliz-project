package com.mobiliz.startup;

import com.mobiliz.request.CompanyRequest;
import com.mobiliz.service.CompanyService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoad {

    private final CompanyService companyService;

    public DataLoad(CompanyService companyService) {
        this.companyService = companyService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        CompanyRequest mobiliz = new CompanyRequest("Mobiliz", 2L, "Ali", "Aktar");
        CompanyRequest navigator = new CompanyRequest("Navigator", 3L, "Zeynep", "Sever");
        CompanyRequest tracker = new CompanyRequest("Tracker", 4L, "Can", "Tok");

        List<CompanyRequest> companyRequests = List.of(mobiliz, navigator, tracker);
        companyRequests.forEach(company -> companyService.createCompany(company.getAdminId(), company));


    }

}
