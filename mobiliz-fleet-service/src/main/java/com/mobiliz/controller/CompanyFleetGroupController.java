package com.mobiliz.controller;

import com.mobiliz.response.CompanyFleetDistrictsGroupResponse;
import com.mobiliz.response.CompanyFleetGroupResponse;
import com.mobiliz.service.CompanyFleetGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fleets")
public class CompanyFleetGroupController {

    private final CompanyFleetGroupService companyFleetGroupService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyFleetGroupController(CompanyFleetGroupService companyFleetGroupService) {
        this.companyFleetGroupService = companyFleetGroupService;
    }

    @GetMapping
    public ResponseEntity<CompanyFleetDistrictsGroupResponse> getCompanyFleet(@RequestHeader("Authorization") String header) {
        CompanyFleetDistrictsGroupResponse fleetGroupResponse = companyFleetGroupService
                .getCompanyFleetById(header);
        return ResponseEntity.ok(fleetGroupResponse);
    }

    @GetMapping("/companies")
    public ResponseEntity<CompanyFleetGroupResponse> getCompanyFleetsByFleetId(
            @RequestHeader("Authorization") String header) {
        CompanyFleetGroupResponse fleetGroupResponse = companyFleetGroupService
                .getCompanyFleetsByFleetId(header);
        return ResponseEntity.ok(fleetGroupResponse);

    }
    @DeleteMapping
    public ResponseEntity<String> deleteCompanyFleetGroup(
            @RequestHeader("Authorization") String header) {
        String response = companyFleetGroupService
                .deleteCompanyFleetGroup(header);
        return ResponseEntity.ok(response);
    }

}
