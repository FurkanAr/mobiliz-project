package com.mobiliz.client;

import com.mobiliz.client.response.CompanyDistrictCompanyGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "CompanyGroup", url = "http://localhost:9095/api")
public interface CompanyGroupClient {
    @GetMapping(value = "/companygroups/companydistrictgroups/{districtGroupId}")
    public List<CompanyDistrictCompanyGroupResponse> getCompanyGroupsByFleetGroupId(
            @RequestHeader("Authorization") String header, @PathVariable Long districtGroupId);
}
