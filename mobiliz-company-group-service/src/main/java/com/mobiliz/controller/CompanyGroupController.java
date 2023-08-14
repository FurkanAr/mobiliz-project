package com.mobiliz.controller;

import com.mobiliz.client.request.UserCompanyGroupSaveRequest;
import com.mobiliz.client.response.VehicleResponseStatus;
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
            @RequestHeader("Authorization") String header,
            @RequestParam Long districtGroupId) {

        List<CompanyGroupResponse> companyGroupResponses = companyGroupService
                .getCompanyGroupsByDistrictGroupIdAndFleetId(header, districtGroupId);
        return ResponseEntity.ok(companyGroupResponses);
    }

    @GetMapping("/{companyGroupId}")
    public ResponseEntity<CompanyGroupResponse> getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(
            @RequestHeader("Authorization") String header, @RequestParam Long fleetId,
            @RequestParam Long districtGroupId,
            @PathVariable Long companyGroupId) {

        CompanyGroupResponse companyGroupResponse = companyGroupService
                .getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(header, fleetId, districtGroupId, companyGroupId);
        return ResponseEntity.ok(companyGroupResponse);
    }

    @PostMapping
    public ResponseEntity<CompanyGroupResponse> createCompanyGroup(
            @RequestHeader("Authorization") String header,
            @RequestParam Long districtGroupId,
            @RequestBody @Valid CompanyGroupRequest companyGroupRequest) {

        CompanyGroupResponse companyGroupResponse = companyGroupService
                .createCompanyGroup(header, districtGroupId, companyGroupRequest);

        return new ResponseEntity<>(companyGroupResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyGroupResponse> updateCompanyGroup(@RequestHeader("Authorization") String header,
                                                                   @RequestParam Long districtGroupId,
                                                                   @RequestParam Long companyGroupId,
                                                                   @RequestBody @Valid CompanyGroupUpdateRequest companyGroupUpdateRequest) {

        CompanyGroupResponse companyGroupResponse = companyGroupService
                .updateCompanyGroup(header,  districtGroupId, companyGroupId, companyGroupUpdateRequest);

        return ResponseEntity.ok(companyGroupResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyGroup(@RequestHeader("Authorization") String header,
                                                     @RequestParam Long companyDistrictGroupId,
                                                     @RequestParam Long companyGroupId
    ) {
        String response = companyGroupService
                .deleteCompany(header,companyDistrictGroupId, companyGroupId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{companyGroupId}")
    public ResponseEntity<VehicleResponseStatus> saveCompanyGroupUser(@RequestHeader("Authorization") String header,
                                                                      @PathVariable Long companyGroupId,
                                                                      @RequestParam Long fleetId,
                                                                      @RequestParam Long districtGroupId,

                                                                      @RequestBody UserCompanyGroupSaveRequest userCompanyGroupSaveRequest) {
        VehicleResponseStatus status = companyGroupService
                .saveCompanyGroupUser(header, companyGroupId, fleetId, districtGroupId, userCompanyGroupSaveRequest);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }


    @GetMapping("/companydistrictgroups/{districtGroupId}")
    public ResponseEntity<List<CompanyGroupResponse>> getCompanyGroupsByDistrictGroupId(
            @RequestHeader("Authorization") String header,
            @RequestParam Long fleetId,
            @PathVariable Long districtGroupId) {

        List<CompanyGroupResponse> companyGroupResponses = companyGroupService
                .getCompanyGroupsByDistrictGroupId(header, fleetId, districtGroupId);
        return ResponseEntity.ok(companyGroupResponses);
    }



}
