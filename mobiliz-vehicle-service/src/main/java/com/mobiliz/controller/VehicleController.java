package com.mobiliz.controller;

import com.mobiliz.request.VehicleRequest;
import com.mobiliz.response.VehicleResponse;
import com.mobiliz.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehicles(@RequestParam Long adminId) {
        List<VehicleResponse> vehicleResponses = vehicleService.getCompanyVehicles(adminId);
        return ResponseEntity.ok(vehicleResponses);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponse> getVehicle(@RequestParam Long adminId , @PathVariable Long vehicleId){
        VehicleResponse vehicleResponse = vehicleService.getByVehicleId(adminId, vehicleId);
        return ResponseEntity.ok(vehicleResponse);
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> createNewVehicle(@RequestParam Long adminId, @RequestBody @Valid VehicleRequest vehicleRequest) {
        VehicleResponse vehicleResponse = vehicleService.createVehicle(adminId, vehicleRequest);
        return new ResponseEntity<>(vehicleResponse, HttpStatus.CREATED);
    }



}
