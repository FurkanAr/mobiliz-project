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
    public ResponseEntity<List<CompanyFleetGroupResponse>> getAllCompanyFleetGroups(@RequestHeader("Authorization") String header) {
        List<CompanyFleetGroupResponse> fleetGroupResponse = companyFleetGroupService
                .getCompanyFleetGroups(header);
        return ResponseEntity.ok(fleetGroupResponse);
    }

    @GetMapping("/{fleetId}")
    public ResponseEntity<CompanyFleetGroupResponse> getCompanyFleetById(@RequestHeader("Authorization") String header,
                                                                         @PathVariable Long fleetId) {
        CompanyFleetGroupResponse fleetGroupResponse = companyFleetGroupService
                .getCompanyFleetById(header, fleetId);

        return ResponseEntity.ok(fleetGroupResponse);

    }

    @PostMapping
    public ResponseEntity<CompanyFleetGroupResponse> createCompanyFleetGroup(@RequestHeader("Authorization") String header,
                                                                             @RequestBody @Valid CompanyFleetGroupRequest companyFleetGroupRequest) {
        CompanyFleetGroupResponse companyFleetGroupResponse = companyFleetGroupService
                .createCompanyFleetGroup(header, companyFleetGroupRequest);

        return new ResponseEntity<>(companyFleetGroupResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyFleetGroupResponse> updateCompanyFleetGroup(
            @RequestHeader("Authorization") String header,
            @RequestParam Long companyFleetGroupId,
            @RequestBody @Valid CompanyFleetUpdateRequest companyFleetGroupRequest) {

        CompanyFleetGroupResponse companyFleetGroupResponse = companyFleetGroupService
                .updateCompanyFleetGroup(header, companyFleetGroupId, companyFleetGroupRequest);

        return ResponseEntity.ok(companyFleetGroupResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyFleetGroup(
            @RequestHeader("Authorization") String header,
            @RequestParam Long companyFleetGroupId) {
        String response = companyFleetGroupService
                .deleteCompanyFleetGroup(header, companyFleetGroupId);

        return ResponseEntity.ok(response);
    }

}
