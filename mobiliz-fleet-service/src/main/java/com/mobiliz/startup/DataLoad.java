package com.mobiliz.startup;

import com.mobiliz.client.AuthServiceClient;
import com.mobiliz.client.CompanyServiceClient;
import com.mobiliz.client.request.TokenRequest;
import com.mobiliz.request.CompanyFleetGroupRequest;
import com.mobiliz.service.CompanyFleetGroupService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoad {

    private final CompanyFleetGroupService companyFleetGroupService;
    private final AuthServiceClient authServiceClient;

    public DataLoad(CompanyFleetGroupService companyFleetGroupService, AuthServiceClient authServiceClient) {
        this.companyFleetGroupService = companyFleetGroupService;
        this.authServiceClient = authServiceClient;
    }


    public String setHeader(Long adminId){
        TokenRequest tokenRequest = new TokenRequest(adminId);
        return authServiceClient.token(tokenRequest);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void init() {



        CompanyFleetGroupRequest istanbulFiloMobiliz = new CompanyFleetGroupRequest("Istanbul Filo", 1L);
        CompanyFleetGroupRequest ankaraFiloMobiliz = new CompanyFleetGroupRequest("Ankara Filo", 1L);
        CompanyFleetGroupRequest bursaFiloMobiliz = new CompanyFleetGroupRequest("Bursa Filo", 1L);

        CompanyFleetGroupRequest istanbulFiloNavigator = new CompanyFleetGroupRequest("Istanbul Filo", 2L);
        CompanyFleetGroupRequest ankaraFiloNavigator = new CompanyFleetGroupRequest("Ankara Filo", 2L);

        List<CompanyFleetGroupRequest> companyFleetGroupRequestsMobiliz = List.of(istanbulFiloMobiliz, ankaraFiloMobiliz, bursaFiloMobiliz);

        companyFleetGroupRequestsMobiliz.forEach(company -> companyFleetGroupService.createCompanyFleetGroup(setHeader(2L), 2L, company));

        List<CompanyFleetGroupRequest> companyFleetGroupRequestsNavigator = List.of(istanbulFiloNavigator, ankaraFiloNavigator);
        companyFleetGroupRequestsNavigator.forEach(company -> companyFleetGroupService.createCompanyFleetGroup(setHeader(3L),3L, company));



    }

}
