package com.mobiliz.client;

import com.mobiliz.client.request.UserCompanyGroupSaveRequest;
import com.mobiliz.client.response.CompanyGroupResponse;
import com.mobiliz.client.response.VehicleResponseStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "CompanyGroup", url = "http://localhost:9095/api")
public interface CompanyGroupClient {

    @GetMapping(value = "/companygroups/{companyGroupId}")
    public CompanyGroupResponse getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(
            @RequestHeader("Authorization") String header,
            @RequestParam Long fleetId,
            @RequestParam Long districtGroupId,
            @PathVariable Long companyGroupId
    );

    @PostMapping(value = "/companygroups/{companyGroupId}")
    public VehicleResponseStatus saveCompanyGroupUser(
            @RequestHeader("Authorization") String header,
            @PathVariable Long companyGroupId,
            @RequestBody UserCompanyGroupSaveRequest request);


    @GetMapping(value = "/companygroups/companydistrictgroups/{districtGroupId}")
    public List<CompanyGroupResponse> getCompanyGroupsByDistrictGroupId(
            @RequestHeader("Authorization") String header,
            @PathVariable Long districtGroupId
    );

}
