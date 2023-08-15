package com.mobiliz.controller;


import com.mobiliz.client.response.VehicleResponse;
import com.mobiliz.service.UserVehicleTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/vehicletree")
public class UserVehicleTreeController {

    private final UserVehicleTreeService userVehicleTreeService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public UserVehicleTreeController(UserVehicleTreeService userVehicleTreeService) {
        this.userVehicleTreeService = userVehicleTreeService;
    }


    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getUserVehicles(@RequestHeader("Authorization") String header) {
        List<VehicleResponse> userVehicles = userVehicleTreeService.getUserVehicles(header);
        return ResponseEntity.ok(userVehicles);
    }








}
