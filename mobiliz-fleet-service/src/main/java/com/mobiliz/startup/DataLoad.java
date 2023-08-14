package com.mobiliz.startup;

import com.mobiliz.request.CompanyFleetGroupRequest;
import com.mobiliz.service.CompanyFleetGroupService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoad {

    private final CompanyFleetGroupService companyFleetGroupService;

    public DataLoad(CompanyFleetGroupService companyFleetGroupService) {
        this.companyFleetGroupService = companyFleetGroupService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        CompanyFleetGroupRequest istanbulFiloMobiliz = new CompanyFleetGroupRequest(
                "Istanbul Filo", 1L, "Mobiliz", 1L, "Funda" ,"Kar" );
        CompanyFleetGroupRequest ankaraFiloMobiliz = new CompanyFleetGroupRequest(
                "Ankara Filo", 1L, "Mobiliz", 2L, "Ali", "Aktar");
        CompanyFleetGroupRequest bursaFiloMobiliz = new CompanyFleetGroupRequest(
                "Bursa Filo", 1L, "Mobiliz", 3L, "Zeynep", "Sever");

        CompanyFleetGroupRequest istanbulFiloNavigator = new CompanyFleetGroupRequest(
                "Istanbul Filo", 2L, "Navigator", 4L, "Can", "Tok");
        CompanyFleetGroupRequest ankaraFiloNavigator = new CompanyFleetGroupRequest(
                "Ankara Filo", 2L, "Navigator", 5L, "Akif", "Bıcak");

        CompanyFleetGroupRequest istanbulFiloTracker= new CompanyFleetGroupRequest(
                "Istanbul Filo", 3L, "Tracker", 6L, "Gizem", "Ak");

        List<CompanyFleetGroupRequest> requestList = List.of(istanbulFiloMobiliz, ankaraFiloMobiliz, bursaFiloMobiliz,
                istanbulFiloNavigator, ankaraFiloNavigator, istanbulFiloTracker);

        requestList.forEach(companyFleetGroupService::createCompanyFleetGroup);

    }

}
