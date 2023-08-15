package com.mobiliz.client;

import com.mobiliz.client.response.VehicleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "GlobalVehicle", url = "http://localhost:9096/api")
public interface GlobalVehicleClient {

    @GetMapping(value = "/global/vehicles/vehicle/list")
    public List<VehicleResponse> getUserVehiclesList(
            @RequestHeader("Authorization") String header
    );
}
