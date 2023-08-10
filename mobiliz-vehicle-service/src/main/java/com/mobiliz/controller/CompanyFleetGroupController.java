package com.mobiliz.controller;

import com.mobiliz.request.companyFleetGroup.CompanyFleetGroupRequest;
import com.mobiliz.request.companyFleetGroup.CompanyFleetUpdateRequest;
import com.mobiliz.response.companyFleetGroup.CompanyFleetGroupResponse;
import com.mobiliz.service.CompanyFleetGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/fleetgroups")
public class CompanyFleetGroupController {

    private final CompanyFleetGroupService companyFleetGroupService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyFleetGroupController(CompanyFleetGroupService companyFleetGroupService) {
        this.companyFleetGroupService = companyFleetGroupService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyFleetGroupResponse>> getAllCompanyFleetGroups(@RequestParam Long adminId) {

        List<CompanyFleetGroupResponse> fleetGroupResponse = companyFleetGroupService
                .getCompanyFleetGroups(adminId);

        return ResponseEntity.ok(fleetGroupResponse);

    }

    @PostMapping
    public ResponseEntity<CompanyFleetGroupResponse> createCompanyFleetGroup(@RequestParam Long adminId,
                                                                             @RequestBody @Valid CompanyFleetGroupRequest companyFleetGroupRequest) {
        CompanyFleetGroupResponse companyFleetGroupResponse = companyFleetGroupService
                .createCompanyFleetGroup(adminId, companyFleetGroupRequest);

        return new ResponseEntity<>(companyFleetGroupResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyFleetGroupResponse> updateCompanyFleetGroup(@RequestParam Long adminId,
                                                                             @RequestParam Long companyFleetGroupId,
                                                                             @RequestBody @Valid CompanyFleetUpdateRequest companyFleetGroupRequest) {
        CompanyFleetGroupResponse companyFleetGroupResponse = companyFleetGroupService
                .updateCompanyFleetGroup(adminId, companyFleetGroupId, companyFleetGroupRequest);

        return ResponseEntity.ok(companyFleetGroupResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyFleetGroup(@RequestParam Long adminId,
                                                          @RequestParam Long companyFleetGroupId) {
        String response = companyFleetGroupService
                .deleteCompanyFleetGroup(adminId, companyFleetGroupId);

        return ResponseEntity.ok(response);
    }

}
