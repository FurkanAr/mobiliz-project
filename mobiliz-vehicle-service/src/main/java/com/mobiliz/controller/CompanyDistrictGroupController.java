package com.mobiliz.controller;

import com.mobiliz.request.companyDistrictGroup.CompanyDistrictGroupRequest;
import com.mobiliz.request.companyDistrictGroup.CompanyDistrictGroupUpdateRequest;
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
    public ResponseEntity<List<CompanyDistrictGroupResponse>> getAllCompanyDistrictGroups(@RequestParam Long adminId) {

        List<CompanyDistrictGroupResponse> companyDistrictGroups = companyDistrictGroupService
                .getCompanyDistrictGroups(adminId);

        return ResponseEntity.ok(companyDistrictGroups);
    }

    @PostMapping
    public ResponseEntity<CompanyDistrictGroupResponse> createCompanyDistrictGroup(@RequestParam Long adminId,
                                                                             @RequestBody @Valid CompanyDistrictGroupRequest companyDistrictGroupRequest) {
        CompanyDistrictGroupResponse companyDistrictGroup = companyDistrictGroupService
                .createCompanyDistrictGroup(adminId, companyDistrictGroupRequest);

        return new ResponseEntity<>(companyDistrictGroup, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyDistrictGroupResponse> updateCompanyDistrictGroup(@RequestParam Long adminId,
                                                                             @RequestParam Long companyFleetGroupId,
                                                                             @RequestParam Long companyDistrictGroupId,
                                                                             @RequestBody @Valid CompanyDistrictGroupUpdateRequest companyDistrictGroupUpdateRequest) {
        CompanyDistrictGroupResponse companyDistrictGroup = companyDistrictGroupService
                .updateCompanyDistrictGroup(adminId, companyFleetGroupId, companyDistrictGroupId, companyDistrictGroupUpdateRequest);

        return ResponseEntity.ok(companyDistrictGroup);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyDistrictGroup(@RequestParam Long adminId,
                                                             @RequestParam Long companyFleetGroupId,
                                                             @RequestParam Long companyDistrictGroupId) {
        String response = companyDistrictGroupService
                .deleteCompanyFleetGroup(adminId, companyFleetGroupId, companyDistrictGroupId);

        return ResponseEntity.ok(response);
    }


}
