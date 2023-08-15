package com.mobiliz.client;

import com.mobiliz.client.request.UserCompanyDistrictGroupSaveRequest;
import com.mobiliz.client.response.CompanyDistrictGroupResponse;
import com.mobiliz.client.response.VehicleResponseStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "CompanyDistrictGroup", url = "http://localhost:9094/api")
public interface CompanyDistrictGroupServiceClient {

    @GetMapping(value = "/districtgroups/{districtGroupId}")
    public CompanyDistrictGroupResponse getCompanyDistrictGroupsByFleetIdAndDistrictId(@RequestHeader("Authorization")
                                                                                       String header,
                                                                                       @PathVariable Long districtGroupId
    );

    @PostMapping(value = "/districtgroups/{districtGroupId}")
    public VehicleResponseStatus saveCompanyDistrictGroupUser(@RequestHeader("Authorization") String header,
                                                              @RequestParam Long districtGroupId);
}
