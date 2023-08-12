package com.mobiliz.startup;

import com.mobiliz.client.AuthServiceClient;
import com.mobiliz.client.request.TokenRequest;
import com.mobiliz.request.CompanyRequest;
import com.mobiliz.service.CompanyService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoad {

    private final CompanyService companyService;
    private final AuthServiceClient authServiceClient;

    public DataLoad(CompanyService companyService, AuthServiceClient authServiceClient) {
        this.companyService = companyService;
        this.authServiceClient = authServiceClient;
    }

    public String setHeader(Long adminId) {
        TokenRequest tokenRequest = new TokenRequest(adminId);
        return authServiceClient.token(tokenRequest);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        CompanyRequest mobiliz = new CompanyRequest("Mobiliz");
        CompanyRequest navigator = new CompanyRequest("Navigator");
        CompanyRequest tracker = new CompanyRequest("Tracker");

        companyService.createCompany(setHeader(2L),mobiliz);
        companyService.createCompany(setHeader(3L),navigator);
        companyService.createCompany(setHeader(4L),tracker);

    }

}
