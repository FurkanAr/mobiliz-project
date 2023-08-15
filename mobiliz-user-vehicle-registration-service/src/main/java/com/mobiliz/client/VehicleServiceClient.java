package com.mobiliz.client;

import com.mobiliz.client.response.VehicleResponseStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "VehicleServiceClient", url = "http://localhost:9096/api")
public interface VehicleServiceClient {

    @PostMapping("/global/vehicles/{vehicleId}")
    public String addVehicleToUserByVehicleId(
            @RequestHeader("Authorization") String header,
            @PathVariable Long vehicleId);

    @PostMapping("/global/vehicles/companygroups/{companyGroupId}")
    public String addVehicleToUserByCompanyGroupId(
            @RequestHeader("Authorization") String header,
            @PathVariable Long companyGroupId,
            @RequestParam Long districtGroupId);

    @PostMapping("/global/vehicles/districtgroups/{districtGroupId}")
    public String addVehicleToUserByCompanyDistrictGroupId(
            @RequestHeader("Authorization") String header,
            @PathVariable Long districtGroupId);

}
