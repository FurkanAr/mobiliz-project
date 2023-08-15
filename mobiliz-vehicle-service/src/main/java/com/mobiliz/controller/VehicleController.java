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
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehicles(@RequestHeader("Authorization") String header) {
        logger.info("getVehicles method started");
        List<VehicleResponse> vehicleResponses = vehicleService.getCompanyVehicles(header);
        logger.info("vehicleResponses : {}", vehicleResponses);
        logger.info("getVehicles method finished");
        return ResponseEntity.ok(vehicleResponses);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponse> getVehicle(@RequestHeader("Authorization") String header,
                                                      @PathVariable Long vehicleId) {
        logger.info("getVehicle method started");
        VehicleResponse vehicleResponse = vehicleService.getByVehicleId(header, vehicleId);
        logger.info("vehicleResponse : {}", vehicleResponse);
        logger.info("getVehicle method finished");
        return ResponseEntity.ok(vehicleResponse);
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> createNewVehicle(@RequestHeader("Authorization") String header,
                                                            @RequestParam Long districtGroupId,
                                                            @RequestParam Optional<Long> companyGroupId,
                                                            @RequestBody @Valid VehicleRequest vehicleRequest) {
        logger.info("createNewVehicle method started");
        VehicleResponse vehicleResponse = vehicleService.createVehicle(header,
                districtGroupId, companyGroupId, vehicleRequest);
        logger.info("vehicleResponse : {}", vehicleResponse);
        logger.info("createNewVehicle method finished");
        return new ResponseEntity<>(vehicleResponse, HttpStatus.CREATED);
    }




}
