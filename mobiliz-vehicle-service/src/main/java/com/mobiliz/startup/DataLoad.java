package com.mobiliz.startup;

import com.mobiliz.client.AuthServiceClient;
import com.mobiliz.client.request.TokenRequest;
import com.mobiliz.request.VehicleRequest;
import com.mobiliz.service.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DataLoad {
    private final AuthServiceClient authServiceClient;

    private final VehicleService vehicleService;


    public DataLoad(AuthServiceClient authServiceClient, VehicleService vehicleService) {
        this.authServiceClient = authServiceClient;
        this.vehicleService = vehicleService;

    }

    public String setHeader(Long adminId) {
        TokenRequest tokenRequest = new TokenRequest(adminId);
        return authServiceClient.token(tokenRequest);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        VehicleRequest bmw3 = new VehicleRequest("34 KVX 943", "BMW", "BMW 3", "2021");
        bmw3.setLabel("You have to buy!!");
        bmw3.setVehicleIdentificationNumber("TET0D15646");

        vehicleService.createVehicle(setHeader(1L), 1L,  Optional.of(1L), bmw3);


        VehicleRequest bmw5 = new VehicleRequest("34 GQC 951", "BMW", "BMW 5", "2022");
        bmw5.setLabel("You have to buy!!");
        bmw5.setVehicleIdentificationNumber("CCZD15646");
        vehicleService.createVehicle(setHeader(1L), 1L, Optional.of(1L), bmw5);


        VehicleRequest mercedesC = new VehicleRequest("34 TXV 258", "Mercedes", "Mercedes C", "2023");
        mercedesC.setLabel("You have to buy!!");
        mercedesC.setVehicleIdentificationNumber("A0D15646");
        vehicleService.createVehicle(setHeader(1L), 2L,  Optional.empty(), mercedesC);

        VehicleRequest mercedesCLS = new VehicleRequest("34 RWA 946", "Mercedes", "Mercedes CLS", "2022");

        vehicleService.createVehicle(setHeader(1L), 2L,  Optional.empty(), mercedesCLS);


        VehicleRequest mercedesGLA = new VehicleRequest("34 ASD 573", "Mercedes", "Mercedes GLA", "2021");
        vehicleService.createVehicle(setHeader(1L), 1L, Optional.empty(), mercedesGLA);

        VehicleRequest toyotaCorolla = new VehicleRequest("34 LVO 548", "Toyota", "Toyota Corolla", "2019");
        toyotaCorolla.setLabel("You have to buy!!");
        toyotaCorolla.setVehicleIdentificationNumber("CS4515646");
        vehicleService.createVehicle(setHeader(1L), 1L,  Optional.empty(), toyotaCorolla);

        VehicleRequest hondaCivic = new VehicleRequest("06 LVO 548", "Honda", "Honda Civic", "2019");
        vehicleService.createVehicle(setHeader(2L), 3L,  Optional.empty(), hondaCivic);


        VehicleRequest mazda3 = new VehicleRequest("06 KRA 282", "Mazda", "Mazda 3", "2014");
        mazda3.setLabel("You have to buy!!");
        mazda3.setVehicleIdentificationNumber("DF895A1");
        vehicleService.createVehicle(setHeader(2L), 3L, Optional.empty(), mazda3);


        VehicleRequest bmw4 = new VehicleRequest("34 KVX 943", "BMW", "BMW 4", "2021");
        bmw3.setLabel("You have to buy!!");
        bmw3.setVehicleIdentificationNumber("TET0D15646");

        vehicleService.createVehicle(setHeader(4L), 4L,  Optional.of(3L), bmw4);

        VehicleRequest bmw6 = new VehicleRequest("34 GQC 951", "BMW", "BMW 6", "2022");

        bmw6.setLabel("You have to buy!!");
        bmw6.setVehicleIdentificationNumber("CCZD15646");
        vehicleService.createVehicle(setHeader(4L), 4L,  Optional.of(3L), bmw6);



    }

}
