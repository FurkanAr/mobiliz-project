package com.mobiliz.controller;

import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.request.CompanyDistrictGroupRequest;
import com.mobiliz.request.CompanyDistrictGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import com.mobiliz.response.CompanyFleetDistrictGroupResponse;
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
            @RequestHeader("Authorization") String header) {

        List<CompanyDistrictGroupResponse> companyDistrictGroups = companyDistrictGroupService
                .getCompanyDistrictGroupsByFleetId(header);

        return ResponseEntity.ok(companyDistrictGroups);
    }


    @PostMapping
    public ResponseEntity<CompanyDistrictGroupResponse> createCompanyDistrictGroup(
            @RequestHeader("Authorization") String header,
            @RequestBody @Valid CompanyDistrictGroupRequest companyDistrictGroupRequest) {

        CompanyDistrictGroupResponse companyDistrictGroup = companyDistrictGroupService
                .createCompanyDistrictGroup(header, companyDistrictGroupRequest);

        return new ResponseEntity<>(companyDistrictGroup, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyDistrictGroupResponse> updateCompanyDistrictGroup(@RequestHeader("Authorization")
                                                                                   String header,
                                                                                   @RequestParam Long companyDistrictGroupId,
                                                                                   @RequestBody @Valid
                                                                                   CompanyDistrictGroupUpdateRequest
                                                                                           companyDistrictGroupUpdateRequest) {
        CompanyDistrictGroupResponse companyDistrictGroup = companyDistrictGroupService
                .updateCompanyDistrictGroup(header, companyDistrictGroupId,
                        companyDistrictGroupUpdateRequest);

        return ResponseEntity.ok(companyDistrictGroup);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyDistrictGroup(@RequestHeader("Authorization") String header,
                                                             @RequestParam Long companyDistrictGroupId) {
        String response = companyDistrictGroupService
                .deleteCompanyFleetGroup(header, companyDistrictGroupId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{districtGroupId}")
    public ResponseEntity<VehicleResponseStatus> saveCompanyDistrictGroupUser(@RequestHeader("Authorization") String header,
                                                                              @PathVariable Long districtGroupId) {
        VehicleResponseStatus status = companyDistrictGroupService
                .saveCompanyDistrictGroupUser(header, districtGroupId);
        return new ResponseEntity<>(status, HttpStatus.CREATED);

    }

    @GetMapping("/{districtGroupId}")
    public ResponseEntity<CompanyDistrictGroupResponse> getCompanyDistrictGroupsByFleetIdAndDistrictId(
            @RequestHeader("Authorization") String header,
            @PathVariable Long districtGroupId) {
        CompanyDistrictGroupResponse companyDistrictGroups = companyDistrictGroupService
                .getCompanyDistrictGroupsByFleetIdAndDistrictId(header, districtGroupId);

        return ResponseEntity.ok(companyDistrictGroups);
    }

    @GetMapping("/fleets")
    public ResponseEntity<List<CompanyFleetDistrictGroupResponse>> getCompanyFleetDistrictGroupsByFleetId(
            @RequestHeader("Authorization") String header) {
        List<CompanyFleetDistrictGroupResponse> companyDistrictGroups = companyDistrictGroupService
                .getCompanyFleetDistrictGroupsByFleetId(header);

        return ResponseEntity.ok(companyDistrictGroups);
    }


}
