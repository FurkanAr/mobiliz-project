package com.mobiliz.controller;

import com.mobiliz.request.CompanyDistrictGroupRequest;
import com.mobiliz.request.CompanyDistrictGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import com.mobiliz.service.CompanyDistrictGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/districtgroups")
public class CompanyDistrictGroupController {

    private final CompanyDistrictGroupService companyDistrictGroupService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyDistrictGroupController(CompanyDistrictGroupService companyDistrictGroupService) {
        this.companyDistrictGroupService = companyDistrictGroupService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDistrictGroupResponse>> getCompanyDistrictGroupsByFleetId(
            @RequestHeader("Authorization") String header, @RequestParam Long fleetId) {

        List<CompanyDistrictGroupResponse> companyDistrictGroups = companyDistrictGroupService
                .getCompanyDistrictGroupsByFleetId(header, fleetId);

        return ResponseEntity.ok(companyDistrictGroups);
    }

    @GetMapping("/{districtGroupId}")
    public ResponseEntity<CompanyDistrictGroupResponse> getCompanyDistrictGroupsByFleetIdAndDistrictId(
            @RequestHeader("Authorization") String header, @RequestParam Long fleetId, @PathVariable Long districtGroupId) {

       CompanyDistrictGroupResponse companyDistrictGroups = companyDistrictGroupService
                .getCompanyDistrictGroupsByFleetIdAndDistrictId(header, fleetId, districtGroupId);

        return ResponseEntity.ok(companyDistrictGroups);
    }

    @PostMapping
    public ResponseEntity<CompanyDistrictGroupResponse> createCompanyDistrictGroup(
            @RequestHeader("Authorization") String header,
            @RequestParam Long fleetId,
            @RequestBody @Valid CompanyDistrictGroupRequest companyDistrictGroupRequest) {

        CompanyDistrictGroupResponse companyDistrictGroup = companyDistrictGroupService
                .createCompanyDistrictGroup(header, fleetId, companyDistrictGroupRequest);

        return new ResponseEntity<>(companyDistrictGroup, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyDistrictGroupResponse> updateCompanyDistrictGroup(@RequestHeader("Authorization") String header,
                                                                                   @RequestParam Long companyFleetGroupId,
                                                                                   @RequestParam Long companyDistrictGroupId,
                                                                                   @RequestBody @Valid CompanyDistrictGroupUpdateRequest companyDistrictGroupUpdateRequest) {
        CompanyDistrictGroupResponse companyDistrictGroup = companyDistrictGroupService
                .updateCompanyDistrictGroup(header, companyFleetGroupId, companyDistrictGroupId,
                        companyDistrictGroupUpdateRequest);

        return ResponseEntity.ok(companyDistrictGroup);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyDistrictGroup(@RequestHeader("Authorization") String header,
                                                             @RequestParam Long companyFleetGroupId,
                                                             @RequestParam Long companyDistrictGroupId) {
        String response = companyDistrictGroupService
                .deleteCompanyFleetGroup(header, companyFleetGroupId, companyDistrictGroupId);

        return ResponseEntity.ok(response);
    }


}
