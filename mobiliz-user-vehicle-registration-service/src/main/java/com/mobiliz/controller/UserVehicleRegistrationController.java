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
            @PathVariable Long companyGroupId,
            @RequestParam Long districtGroupId) {
        String response = userVehicleRegistrationService.addCompanyGroupVehiclesToUser(header, companyGroupId, districtGroupId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/districtgroups/{districtGroupId}")
    public ResponseEntity<String> addCompanyDistrictGroupVehiclesToUser(
            @RequestHeader("Authorization") String header,
            @PathVariable Long districtGroupId) {
        String response = userVehicleRegistrationService.addCompanyDistrictGroupVehiclesToUser(header, districtGroupId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }





}
