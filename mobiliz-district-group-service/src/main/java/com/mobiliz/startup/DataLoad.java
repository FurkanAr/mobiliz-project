package com.mobiliz.startup;


import com.mobiliz.client.AuthServiceClient;
import com.mobiliz.client.request.TokenRequest;
import com.mobiliz.request.CompanyDistrictGroupRequest;
import com.mobiliz.service.CompanyDistrictGroupService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoad {


    private final CompanyDistrictGroupService companyGroupDistrictService;
    private final AuthServiceClient authServiceClient;

    public DataLoad(CompanyDistrictGroupService companyGroupDistrictService, AuthServiceClient authServiceClient) {
        this.companyGroupDistrictService = companyGroupDistrictService;
        this.authServiceClient = authServiceClient;
    }

    public String setHeader(Long adminId) {
        TokenRequest tokenRequest = new TokenRequest(adminId);
        return authServiceClient.token(tokenRequest);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {


        CompanyDistrictGroupRequest avrupaGrubuMobiliz = new CompanyDistrictGroupRequest("Avrupa Grubu");
        CompanyDistrictGroupRequest asyaGrubuMobiliz = new CompanyDistrictGroupRequest("Asya Grubu");
        CompanyDistrictGroupRequest kuzeyAnkaraGrubuMobiliz = new CompanyDistrictGroupRequest("Kuzey Ankara Grubu");

        CompanyDistrictGroupRequest avrupaGrubuNavigator = new CompanyDistrictGroupRequest("Avrupa Grubu");
        CompanyDistrictGroupRequest kuzeyAnkaraGrubuNavigator = new CompanyDistrictGroupRequest("Kuzey Ankara Grubu");

        companyGroupDistrictService.createCompanyDistrictGroup(setHeader(1L), avrupaGrubuMobiliz);
        companyGroupDistrictService.createCompanyDistrictGroup(setHeader(1L), asyaGrubuMobiliz);
        companyGroupDistrictService.createCompanyDistrictGroup(setHeader(2L),  kuzeyAnkaraGrubuMobiliz);
        companyGroupDistrictService.createCompanyDistrictGroup(setHeader(4L),  avrupaGrubuNavigator);
        companyGroupDistrictService.createCompanyDistrictGroup(setHeader(5L),  kuzeyAnkaraGrubuNavigator);




    }

}
