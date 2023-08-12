package com.mobiliz.client;

import com.mobiliz.client.response.CompanyDistrictGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "CompanyDistrictGroup", url = "http://localhost:9094/api")
public interface CompanyDistrictGroupServiceClient {

    @GetMapping(value = "/districtgroups/{districtGroupId}")
    public CompanyDistrictGroupResponse getCompanyDistrictGroupsByFleetIdAndDistrictId(@RequestHeader("Authorization")
                                                                                       String header,
                                                                                       @RequestParam Long fleetId,
                                                                                       @PathVariable Long districtGroupId
    );
}
