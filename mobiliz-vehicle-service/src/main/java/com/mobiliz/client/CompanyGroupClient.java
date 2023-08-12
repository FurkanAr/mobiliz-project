package com.mobiliz.client;

import com.mobiliz.client.response.CompanyGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "CompanyGroup", url = "http://localhost:9095/api")
public interface CompanyGroupClient {

    @GetMapping(value = "/companygroups/{companyGroupId}")
    public CompanyGroupResponse getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(
            @RequestHeader("Authorization") String header,
            @RequestParam Long fleetId,
            @RequestParam Long districtGroupId,
            @PathVariable Long companyGroupId
    );


}
