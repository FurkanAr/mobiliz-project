package com.mobiliz.startup;

import com.mobiliz.request.*;
import com.mobiliz.service.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoad {

    private final VehicleService vehicleService;
    private final CompanyService companyService;
    private final CompanyFleetGroupService companyFleetGroupService;
    private final CompanyGroupDistrictService companyGroupDistrictService;
    private final CompanyGroupService companyGroupService;


    public DataLoad(VehicleService vehicleService, CompanyService companyService,
                    CompanyFleetGroupService companyFleetGroupService, CompanyGroupDistrictService companyGroupDistrictService, CompanyGroupService companyGroupService) {
        this.vehicleService = vehicleService;
        this.companyService = companyService;
        this.companyFleetGroupService = companyFleetGroupService;
        this.companyGroupDistrictService = companyGroupDistrictService;
        this.companyGroupService = companyGroupService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        CompanyRequest mobiliz = new CompanyRequest("Mobiliz", 2L, "Ali", "Aktar");
        CompanyRequest navigator = new CompanyRequest("Navigator", 3L, "Zeynep", "Sever");
        CompanyRequest tracker = new CompanyRequest("Tracker", 4L, "Can", "Tok");

        List<CompanyRequest> companyRequests = List.of(mobiliz, navigator, tracker);
        companyRequests.forEach(companyService::createCompany);

        CompanyFleetGroupRequest istanbulFiloMobiliz = new CompanyFleetGroupRequest("Istanbul Filo", 1L);
        CompanyFleetGroupRequest ankaraFiloMobiliz = new CompanyFleetGroupRequest("Ankara Filo", 1L);
        CompanyFleetGroupRequest bursaFiloMobiliz = new CompanyFleetGroupRequest("Bursa Filo", 1L);

        CompanyFleetGroupRequest istanbulFiloNavigator = new CompanyFleetGroupRequest("Istanbul Filo", 2L);
        CompanyFleetGroupRequest ankaraFiloNavigator = new CompanyFleetGroupRequest("Ankara Filo", 2L);


        List<CompanyFleetGroupRequest> companyFleetGroupRequests = List.of(istanbulFiloMobiliz, ankaraFiloMobiliz, bursaFiloMobiliz, istanbulFiloNavigator, ankaraFiloNavigator);
        companyFleetGroupRequests.forEach(companyFleetGroupService::createFleet);

        CompanyDistrictGroupRequest avrupaGrubuMobiliz = new CompanyDistrictGroupRequest("Avrupa Grubu", 1L,1L);
        CompanyDistrictGroupRequest asyaGrubuMobiliz = new CompanyDistrictGroupRequest("Asya Grubu", 1L, 1L);
        CompanyDistrictGroupRequest kuzeyAnkaraGrubuMobiliz = new CompanyDistrictGroupRequest("Kuzey Ankara Grubu", 2L, 1L);

        CompanyDistrictGroupRequest avrupaGrubuNavigator = new CompanyDistrictGroupRequest("Avrupa Grubu", 4L,2L);
        CompanyDistrictGroupRequest kuzeyAnkaraGrubuNavigator = new CompanyDistrictGroupRequest("Kuzey Ankara Grubu", 5L, 2L);


        List<CompanyDistrictGroupRequest> companyDistrictGroupRequests = List.of(avrupaGrubuMobiliz, asyaGrubuMobiliz, kuzeyAnkaraGrubuMobiliz, avrupaGrubuNavigator, kuzeyAnkaraGrubuNavigator);
        companyDistrictGroupRequests.forEach(companyGroupDistrictService::createCompanyDistrictGroup);

        CompanyGroupRequest avrupaKuryeMobiliz = new CompanyGroupRequest("Avrupa Kurye", 1L, 1L, 1L);
        CompanyGroupRequest asyaKuryeMobiliz = new CompanyGroupRequest("Asya Kurye", 2L, 1L, 1L);
        CompanyGroupRequest avrupaKuryeNavigator = new CompanyGroupRequest("Avrupa Kurye", 4L, 2L,4L);

        List<CompanyGroupRequest> companyGroupRequests = List.of(avrupaKuryeMobiliz, asyaKuryeMobiliz, avrupaKuryeNavigator);
        companyGroupRequests.forEach(companyGroupService::createCompanyGroup);

        VehicleRequest bmw3 = new VehicleRequest("34 KVX 943", "BMW", "BMW 3", "2021",  1L, 1L, 1L);
        VehicleRequest bmw5 = new VehicleRequest("34 GQC 951", "BMW", "BMW 5", "2022",  1L,1L, 1L);
        bmw3.setCompanyGroupId(1L);
        bmw3.setLabel("You have to buy!!");
        bmw3.setVehicleIdentificationNumber("TET0D15646");
        bmw5.setCompanyGroupId(1L);
        bmw5.setLabel("You have to buy!!");
        bmw5.setVehicleIdentificationNumber("CCZD15646");

        VehicleRequest mercedesC = new VehicleRequest("34 TXV 258", "Mercedes", "Mercedes C", "2023",  1L,  1L, 1L);
        mercedesC.setLabel("You have to buy!!");
        mercedesC.setVehicleIdentificationNumber("A0D15646");

        VehicleRequest mercedesCLS = new VehicleRequest("34 RWA 946", "Mercedes", "Mercedes CLS", "2022",  1L,  1L, 1L);
        VehicleRequest mercedesGLA = new VehicleRequest("34 ASD 573", "Mercedes", "Mercedes GLA", "2021",  1L,  1L, 1L);

        VehicleRequest toyotaCorolla = new VehicleRequest("34 LVO 548", "Toyota", "Toyota Corolla", "2019",  2L,  1L, 1L);
        toyotaCorolla.setLabel("You have to buy!!");
        toyotaCorolla.setVehicleIdentificationNumber("CS4515646");

        VehicleRequest hondaCivic = new VehicleRequest("06 LVO 548", "Honda", "Honda Civic", "2019",  3L,  1L, 2L);

        VehicleRequest mazda3 = new VehicleRequest("06 KRA 282", "Mazda", "Mazda 3", "2014",  3L,  1L, 2L);
        mazda3.setLabel("You have to buy!!");
        mazda3.setVehicleIdentificationNumber("DF895A1");

        VehicleRequest bmw4 = new VehicleRequest("34 KVX 943", "BMW", "BMW 4", "2021",  4L, 2L, 4L);
        VehicleRequest bmw6 = new VehicleRequest("34 GQC 951", "BMW", "BMW 6", "2022",  4L,2L, 5L);
        bmw3.setCompanyGroupId(3L);
        bmw3.setLabel("You have to buy!!");
        bmw3.setVehicleIdentificationNumber("TET0D15646");
        bmw5.setCompanyGroupId(3L);
        bmw5.setLabel("You have to buy!!");
        bmw5.setVehicleIdentificationNumber("CCZD15646");

        List<VehicleRequest> vehicleRequests = List.of(bmw3, bmw5, mercedesC, mercedesCLS, mercedesGLA, toyotaCorolla, hondaCivic, mazda3, bmw4, bmw6);
        vehicleRequests.forEach(vehicleService::createVehicle);

    }

}
