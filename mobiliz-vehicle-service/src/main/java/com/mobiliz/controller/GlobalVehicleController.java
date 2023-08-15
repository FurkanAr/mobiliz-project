package com.mobiliz.controller;

import com.mobiliz.response.VehicleResponse;
import com.mobiliz.service.GlobalVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/global/vehicles")
public class GlobalVehicleController {

    private final GlobalVehicleService globalVehicleService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public GlobalVehicleController(GlobalVehicleService globalVehicleService) {
        this.globalVehicleService = globalVehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehicles(@RequestHeader("Authorization") String header) {
        logger.info("getVehicles method started");
        List<VehicleResponse> vehicleResponses = globalVehicleService.getCompanyVehicles(header);
        logger.info("vehicleResponses : {}", vehicleResponses);
        logger.info("getVehicles method finished");
        return ResponseEntity.ok(vehicleResponses);
    }

    @PostMapping("/{vehicleId}")
    public ResponseEntity<String> addVehicleToUserByVehicleId(@RequestHeader("Authorization") String header,
            @PathVariable Long vehicleId) {
        logger.info("addVehicleToUserByVehicleId method started");
        String response = globalVehicleService.addVehicleToUserByVehicleId(header, vehicleId);
        logger.info("response : {}", response);
        logger.info("addVehicleToUserByVehicleId method finished");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/companygroups/{companyGroupId}")
    public ResponseEntity<String> addVehicleToUserByCompanyGroupId(@RequestHeader("Authorization") String header,
            @PathVariable Long companyGroupId, @RequestParam Long districtGroupId) {
        logger.info("addVehicleToUserByCompanyGroupId method started");
        String response = globalVehicleService.addVehicleToUserByCompanyGroupId(header, companyGroupId, districtGroupId);
        logger.info("response : {}", response);
        logger.info("addVehicleToUserByCompanyGroupId method finished");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/districtgroups/{districtGroupId}")
    public ResponseEntity<String> addVehicleToUserByCompanyDistrictGroupId(@RequestHeader("Authorization") String header,
            @PathVariable Long districtGroupId) {
        logger.info("addVehicleToUserByCompanyDistrictGroupId method started");
        String response = globalVehicleService.addVehicleToUserByCompanyDistrictGroupId(header, districtGroupId);
        logger.info("response : {}", response);
        logger.info("addVehicleToUserByCompanyDistrictGroupId method finished");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/vehicle/list")
    public ResponseEntity<List<VehicleResponse>> getUserVehiclesList(@RequestHeader("Authorization") String header) {
        logger.info("getUserVehiclesList method started");
        List<VehicleResponse> vehicleResponses = globalVehicleService.getUserVehiclesList(header);
        logger.info("vehicleResponses : {}", vehicleResponses);
        logger.info("getUserVehiclesList method finished");
        return ResponseEntity.ok(vehicleResponses);
    }

    @GetMapping("/vehicle/tree")
    public ResponseEntity<List<VehicleResponse>> getUserVehiclesTree(@RequestHeader("Authorization") String header) {
        logger.info("getUserVehiclesTree method started");
        List<VehicleResponse> vehicleResponses = globalVehicleService.getUserVehiclesTree(header);
        logger.info("vehicleResponses : {}", vehicleResponses);
        logger.info("getUserVehiclesTree method finished");
        return ResponseEntity.ok(vehicleResponses);
    }




}

