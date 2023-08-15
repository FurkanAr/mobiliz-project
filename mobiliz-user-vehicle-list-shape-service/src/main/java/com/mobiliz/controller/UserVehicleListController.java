package com.mobiliz.controller;


import com.mobiliz.client.response.VehicleResponse;
import com.mobiliz.service.UserVehicleListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/vehiclelist")
public class  UserVehicleListController {

    private final UserVehicleListService userVehicleListService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public UserVehicleListController(UserVehicleListService userVehicleListService) {
        this.userVehicleListService = userVehicleListService;
    }


    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getUserVehicles(@RequestHeader("Authorization") String header) {
        List<VehicleResponse> userVehicles = userVehicleListService.getUserVehicles(header);
        return ResponseEntity.ok(userVehicles);
    }








}
