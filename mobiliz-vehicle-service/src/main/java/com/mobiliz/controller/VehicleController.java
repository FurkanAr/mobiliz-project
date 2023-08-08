package com.mobiliz.controller;

import com.mobiliz.request.VehicleRequest;
import com.mobiliz.response.VehicleResponse;
import com.mobiliz.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public List<VehicleResponse> getVehicles() {
        return vehicleService.findAllVehicles();
    }

    @GetMapping("/{vehicleId}")
    public VehicleResponse getVehicle(@PathVariable Long vehicleId){
        return vehicleService.getByVehicleId(vehicleId);
    }

    @PostMapping()
    public VehicleResponse createNewVehicle(@RequestHeader("Authorization") String header, @RequestBody @Valid VehicleRequest vehicleRequest) {
        return vehicleService.createVehicle(vehicleRequest);
    }

}
