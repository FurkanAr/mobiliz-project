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


        CompanyDistrictGroupRequest avrupaGrubuMobiliz = new CompanyDistrictGroupRequest("Avrupa Grubu", 1L, 1L);
        CompanyDistrictGroupRequest asyaGrubuMobiliz = new CompanyDistrictGroupRequest("Asya Grubu", 1L, 1L);
        CompanyDistrictGroupRequest kuzeyAnkaraGrubuMobiliz = new CompanyDistrictGroupRequest("Kuzey Ankara Grubu", 2L, 1L);

        CompanyDistrictGroupRequest avrupaGrubuNavigator = new CompanyDistrictGroupRequest("Avrupa Grubu", 4L, 2L);
        CompanyDistrictGroupRequest kuzeyAnkaraGrubuNavigator = new CompanyDistrictGroupRequest("Kuzey Ankara Grubu", 5L, 2L);


        List<CompanyDistrictGroupRequest> companyDistrictGroupRequests = List.of(avrupaGrubuMobiliz, asyaGrubuMobiliz, kuzeyAnkaraGrubuMobiliz);
        companyDistrictGroupRequests.forEach(company -> companyGroupDistrictService.createCompanyDistrictGroup(setHeader(2L), 2L, company.getCompanyFleetGroupId(), company));

        List<CompanyDistrictGroupRequest> companyDistrictGroupRequestsNavigator = List.of(avrupaGrubuNavigator, kuzeyAnkaraGrubuNavigator);
        companyDistrictGroupRequestsNavigator.forEach(company -> companyGroupDistrictService.createCompanyDistrictGroup(setHeader(3L), 3L, company.getCompanyFleetGroupId(), company));


    }

}
