package com.mobiliz.controller;

import com.mobiliz.service.UserVehicleRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/uservehicles")
public class UserVehicleRegistrationController {

    private final UserVehicleRegistrationService userVehicleRegistrationService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public UserVehicleRegistrationController(UserVehicleRegistrationService userVehicleRegistrationService) {
        this.userVehicleRegistrationService = userVehicleRegistrationService;
    }


    @PostMapping("/{vehicleId}")
    public ResponseEntity<String> addVehicleToUser(@RequestHeader("Authorization") String header,
            @PathVariable Long vehicleId) {
        logger.info("addVehicleToUser method started");
        String response = userVehicleRegistrationService.addVehicleToUser(header, vehicleId);
        logger.info("response : {}", response);
        logger.info("addVehicleToUser method finished");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/companygroups/{companyGroupId}")
    public ResponseEntity<String> addCompanyGroupVehiclesToUser(
            @RequestHeader("Authorization") String header, @PathVariable Long companyGroupId,
            @RequestParam Long districtGroupId) {
        logger.info("addCompanyGroupVehiclesToUser method started");
        String response = userVehicleRegistrationService.addCompanyGroupVehiclesToUser(header, companyGroupId, districtGroupId);
        logger.info("response : {}", response);
        logger.info("addCompanyGroupVehiclesToUser method finished");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/districtgroups/{districtGroupId}")
    public ResponseEntity<String> addCompanyDistrictGroupVehiclesToUser(@RequestHeader("Authorization") String header,
            @PathVariable Long districtGroupId) {
        logger.info("addCompanyDistrictGroupVehiclesToUser method started");
        String response = userVehicleRegistrationService.addCompanyDistrictGroupVehiclesToUser(header, districtGroupId);
        logger.info("response : {}", response);
        logger.info("addCompanyDistrictGroupVehiclesToUser method finished");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }





}
