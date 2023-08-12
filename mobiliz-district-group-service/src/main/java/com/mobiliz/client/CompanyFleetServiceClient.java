package com.mobiliz.client;

import com.mobiliz.client.response.CompanyFleetGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "CompanyFleet", url = "http://localhost:9093/api")
public interface CompanyFleetServiceClient {

    @GetMapping(value = "/fleets/{fleetId}")
    public CompanyFleetGroupResponse getCompanyFleetById(@RequestHeader("Authorization") String header,
                                                         @PathVariable Long fleetId);
}
