package com.mobiliz.controller;

import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.request.CompanyGroupRequest;
import com.mobiliz.request.CompanyGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictCompanyGroupResponse;
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
    public ResponseEntity<List<CompanyGroupResponse>> getCompanyGroupsByCompanyIdAndFleetId(
            @RequestHeader("Authorization") String header) {

        logger.info("getCompanyGroupsByCompanyIdAndFleetId method started");
        List<CompanyGroupResponse> companyGroupResponses = companyGroupService
                .getCompanyGroupsByCompanyIdAndFleetId(header);
        logger.info("companyGroupResponses: {}", companyGroupResponses);
        logger.info("getCompanyGroupsByCompanyIdAndFleetId method finished");
        return ResponseEntity.ok(companyGroupResponses);
    }

    @GetMapping("/{companyGroupId}")
    public ResponseEntity<CompanyGroupResponse> getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(
            @RequestHeader("Authorization") String header,
            @RequestParam Long districtGroupId,
            @PathVariable Long companyGroupId) {
        logger.info("getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId method started");
        CompanyGroupResponse companyGroupResponse = companyGroupService
                .getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(header, districtGroupId, companyGroupId);
        logger.info("companyGroupResponse: {}", companyGroupResponse);
        logger.info("getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId method finished");
        return ResponseEntity.ok(companyGroupResponse);
    }


    @PostMapping
    public ResponseEntity<CompanyGroupResponse> createCompanyGroup(
            @RequestHeader("Authorization") String header,
            @RequestParam Long districtGroupId,
            @RequestBody @Valid CompanyGroupRequest companyGroupRequest) {
        logger.info("createCompanyGroup method started");
        CompanyGroupResponse companyGroupResponse = companyGroupService
                .createCompanyGroup(header, districtGroupId, companyGroupRequest);
        logger.info("companyGroupResponse created: {}", companyGroupResponse);
        logger.info("createCompanyGroup method finished");
        return new ResponseEntity<>(companyGroupResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyGroupResponse> updateCompanyGroup(@RequestHeader("Authorization") String header,
                                                                   @RequestParam Long districtGroupId,
                                                                   @RequestParam Long companyGroupId,
                                                                   @RequestBody @Valid CompanyGroupUpdateRequest companyGroupUpdateRequest) {
        logger.info("updateCompanyGroup method started");

        CompanyGroupResponse companyGroupResponse = companyGroupService
                .updateCompanyGroup(header, districtGroupId, companyGroupId, companyGroupUpdateRequest);
        logger.info("companyGroupResponse updated: {}", companyGroupResponse);
        logger.info("updateCompanyGroup method finished");
        return ResponseEntity.ok(companyGroupResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyGroup(@RequestHeader("Authorization") String header,
                                                     @RequestParam Long companyDistrictGroupId,
                                                     @RequestParam Long companyGroupId) {
        logger.info("deleteCompanyGroup method started");
        String response = companyGroupService
                .deleteCompany(header, companyDistrictGroupId, companyGroupId);
        logger.info("response deleted: {}", response);
        logger.info("deleteCompanyGroup method finished");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{companyGroupId}")
    public ResponseEntity<VehicleResponseStatus> saveCompanyGroupUser(@RequestHeader("Authorization") String header,
                                                                      @PathVariable Long companyGroupId,
                                                                      @RequestParam Long districtGroupId) {
        logger.info("saveCompanyGroupUser method started");
        VehicleResponseStatus status = companyGroupService
                .saveCompanyGroupUser(header, companyGroupId, districtGroupId);
        logger.info("status : {}", status);
        logger.info("saveCompanyGroupUser method finished");
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping("/companydistrictgroups/{districtGroupId}")
    public ResponseEntity<List<CompanyDistrictCompanyGroupResponse>> getCompanyGroupsByFleetGroupId(
            @RequestHeader("Authorization") String header,
            @PathVariable Long districtGroupId) {
        logger.info("getCompanyGroupsByFleetGroupId method started");

        List<CompanyDistrictCompanyGroupResponse> companyGroupResponses = companyGroupService
                .getCompanyGroupsByFleetGroupId(header, districtGroupId);
        logger.info("companyGroupResponses : {}", companyGroupResponses);
        logger.info("getCompanyGroupsByFleetGroupId method finished");
        return ResponseEntity.ok(companyGroupResponses);
    }


}
