package com.mobiliz.controller;

import com.mobiliz.response.VehicleResponse;
import com.mobiliz.service.GlobalVehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/global/vehicles")
public class GlobalVehicleController {

    private final GlobalVehicleService globalVehicleService;


    public GlobalVehicleController(GlobalVehicleService globalVehicleService) {
        this.globalVehicleService = globalVehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehicles(@RequestHeader("Authorization") String header) {
        List<VehicleResponse> vehicleResponses = globalVehicleService.getCompanyVehicles(header);
        return ResponseEntity.ok(vehicleResponses);
    }

    @PostMapping("/{vehicleId}")
    public ResponseEntity<String> addVehicleToUserByVehicleId(
            @RequestHeader("Authorization") String header,
            @PathVariable Long vehicleId) {
        String response = globalVehicleService.addVehicleToUserByVehicleId(header, vehicleId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/companygroups/{companyGroupId}")
    public ResponseEntity<String> addVehicleToUserByCompanyGroupId(
            @RequestHeader("Authorization") String header,
            @PathVariable Long companyGroupId) {
        String response = globalVehicleService.addVehicleToUserByCompanyGroupId(header, companyGroupId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/districtgroups/{districtGroupId}")
    public ResponseEntity<String> addVehicleToUserByCompanyDistrictGroupId(
            @RequestHeader("Authorization") String header,
            @RequestParam Long fleetId,
            @PathVariable Long districtGroupId) {
        String response = globalVehicleService.addVehicleToUserByCompanyDistrictGroupId(header, fleetId, districtGroupId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}

