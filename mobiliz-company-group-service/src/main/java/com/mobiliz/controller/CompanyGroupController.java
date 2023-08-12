package com.mobiliz.controller;

import com.mobiliz.request.CompanyGroupRequest;
import com.mobiliz.request.CompanyGroupUpdateRequest;
import com.mobiliz.response.CompanyGroupResponse;
import com.mobiliz.service.CompanyGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/companygroups")
public class CompanyGroupController {

    private final CompanyGroupService companyGroupService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyGroupController(CompanyGroupService companyGroupService) {
        this.companyGroupService = companyGroupService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyGroupResponse>> getCompanyGroupsByDistrictGroupIdByFleetId(
            @RequestHeader("Authorization") String header, @RequestParam Long fleetId,
            @RequestParam Long districtGroupId) {

        List<CompanyGroupResponse> companyGroupResponses = companyGroupService
                .getCompanyGroupsByDistrictGroupIdAndFleetId(header, fleetId, districtGroupId);
        return ResponseEntity.ok(companyGroupResponses);
    }

    @GetMapping("/{companyGroupId}")
    public ResponseEntity<CompanyGroupResponse> getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(
            @RequestHeader("Authorization") String header, @RequestParam Long fleetId,
            @RequestParam Long districtGroupId,
            @PathVariable Long companyGroupId) {

        CompanyGroupResponse companyGroupResponse = companyGroupService
                .getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(header, fleetId, districtGroupId,companyGroupId);
        return ResponseEntity.ok(companyGroupResponse);
    }

    @PostMapping
    public ResponseEntity<CompanyGroupResponse> createCompanyGroup(
            @RequestHeader("Authorization") String header,
            @RequestParam Long fleetId,
            @RequestParam Long districtGroupId,
            @RequestBody @Valid CompanyGroupRequest companyGroupRequest) {

        CompanyGroupResponse companyGroupResponse = companyGroupService
                .createCompanyGroup(header, fleetId, districtGroupId, companyGroupRequest);

        return new ResponseEntity<>(companyGroupResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyGroupResponse> updateCompanyGroup(@RequestHeader("Authorization") String header,
                                                                   @RequestParam Long fleetId,
                                                                   @RequestParam Long districtGroupId,
                                                                   @RequestParam Long companyGroupId,
                                                                   @RequestBody @Valid CompanyGroupUpdateRequest companyGroupUpdateRequest) {

        CompanyGroupResponse companyGroupResponse = companyGroupService
                .updateCompanyGroup(header, fleetId, districtGroupId, companyGroupId, companyGroupUpdateRequest);

        return ResponseEntity.ok(companyGroupResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyGroup(@RequestHeader("Authorization") String header,
                                                     @RequestParam Long companyFleetGroupId,
                                                     @RequestParam Long companyDistrictGroupId,
                                                     @RequestParam Long companyGroupId
    ) {
        String response = companyGroupService
                .deleteCompany(header, companyFleetGroupId, companyDistrictGroupId, companyGroupId);

        return ResponseEntity.ok(response);
    }

}
