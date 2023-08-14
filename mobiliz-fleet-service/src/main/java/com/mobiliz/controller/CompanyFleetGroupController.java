package com.mobiliz.controller;

import com.mobiliz.request.CompanyFleetGroupRequest;
import com.mobiliz.request.CompanyFleetUpdateRequest;
import com.mobiliz.response.CompanyFleetGroupResponse;
import com.mobiliz.service.CompanyFleetGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<CompanyFleetGroupResponse> getCompanyFleet(@RequestHeader("Authorization") String header) {
        CompanyFleetGroupResponse fleetGroupResponse = companyFleetGroupService
                .getCompanyFleetById(header);
        return ResponseEntity.ok(fleetGroupResponse);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyFleetGroupResponse>> getCompanyFleetsByCompanyId(
            @RequestHeader("Authorization") String header) {
        List<CompanyFleetGroupResponse> fleetGroupResponse = companyFleetGroupService
                .getCompanyFleetsByCompanyId(header);
        return ResponseEntity.ok(fleetGroupResponse);

    }

    @PutMapping
    public ResponseEntity<CompanyFleetGroupResponse> updateCompanyFleetGroup(
            @RequestHeader("Authorization") String header,
            @RequestBody @Valid CompanyFleetUpdateRequest companyFleetGroupRequest) {

        CompanyFleetGroupResponse companyFleetGroupResponse = companyFleetGroupService
                .updateCompanyFleetGroup(header, companyFleetGroupRequest);

        return ResponseEntity.ok(companyFleetGroupResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyFleetGroup(
            @RequestHeader("Authorization") String header) {
        String response = companyFleetGroupService
                .deleteCompanyFleetGroup(header);
        return ResponseEntity.ok(response);
    }

}
