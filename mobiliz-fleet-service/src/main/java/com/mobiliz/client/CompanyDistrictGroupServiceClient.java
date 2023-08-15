package com.mobiliz.client;

import com.mobiliz.client.response.CompanyFleetDistrictGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "CompanyDistrictGroup", url = "http://localhost:9094/api")
public interface CompanyDistrictGroupServiceClient {

    @GetMapping(value = "/districtgroups/fleets")
    public List<CompanyFleetDistrictGroupResponse> getCompanyFleetDistrictGroupsByFleetId(
            @RequestHeader("Authorization") String header
    );
}