package com.mobiliz.startup;

import com.mobiliz.client.AuthServiceClient;
import com.mobiliz.client.request.TokenRequest;
import com.mobiliz.request.CompanyGroupRequest;
import com.mobiliz.service.CompanyGroupService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoad {

    private final AuthServiceClient authServiceClient;
    private final CompanyGroupService companyGroupService;

    public DataLoad(AuthServiceClient authServiceClient, CompanyGroupService companyGroupService) {
        this.authServiceClient = authServiceClient;
        this.companyGroupService = companyGroupService;
    }

    public String setHeader(Long adminId) {
        TokenRequest tokenRequest = new TokenRequest(adminId);
        return authServiceClient.token(tokenRequest);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        CompanyGroupRequest avrupaKuryeMobiliz = new CompanyGroupRequest("Avrupa Kurye");
        CompanyGroupRequest asyaKuryeMobiliz = new CompanyGroupRequest("Asya Kurye");
        CompanyGroupRequest avrupaKuryeNavigator = new CompanyGroupRequest("Avrupa Kurye");

        companyGroupService.createCompanyGroup(setHeader(2L),1L, 1L ,avrupaKuryeMobiliz);
        companyGroupService.createCompanyGroup(setHeader(2L),1L, 2L ,asyaKuryeMobiliz);
        companyGroupService.createCompanyGroup(setHeader(3L),4L, 4L ,asyaKuryeMobiliz);


    }

}
