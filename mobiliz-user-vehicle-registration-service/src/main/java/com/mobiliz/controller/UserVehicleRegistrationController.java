package com.mobiliz.controller;

import com.mobiliz.service.UserVehicleRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/uservehicles")
public class UserVehicleRegistrationController {

    private final UserVehicleRegistrationService userVehicleRegistrationService;

    public UserVehicleRegistrationController(UserVehicleRegistrationService userVehicleRegistrationService) {
        this.userVehicleRegistrationService = userVehicleRegistrationService;
    }


    @PostMapping("/{vehicleId}")
    public ResponseEntity<String> addVehicleToUser(
            @RequestHeader("Authorization") String header,
            @PathVariable Long vehicleId) {
        String response = userVehicleRegistrationService.addVehicleToUser(header, vehicleId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/companygroups/{companyGroupId}")
    public ResponseEntity<String> addCompanyGroupVehiclesToUser(
            @RequestHeader("Authorization") String header,
            @PathVariable Long companyGroupId) {
        String response = userVehicleRegistrationService.addCompanyGroupVehiclesToUser(header, companyGroupId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/districtgroups/{districtgroupId}")
    public ResponseEntity<String> addCompanyDistrictGroupVehiclesToUser(
            @RequestHeader("Authorization") String header,
            @RequestParam Long fleetId,
            @PathVariable Long districtgroupId) {
        String response = userVehicleRegistrationService.addCompanyDistrictGroupVehiclesToUser(header,fleetId, districtgroupId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }





}
