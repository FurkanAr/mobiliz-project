package com.mobiliz.controller;

import com.mobiliz.request.companyGroup.CompanyGroupRequest;
import com.mobiliz.request.companyGroup.CompanyGroupUpdateRequest;
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
    public ResponseEntity<List<CompanyGroupResponse>> getAllCompanyGroups(@RequestParam Long adminId) {

        List<CompanyGroupResponse> companyGroupResponses = companyGroupService
                .getCompanyGroups(adminId);

        return ResponseEntity.ok(companyGroupResponses);
    }

    @PostMapping
    public ResponseEntity<CompanyGroupResponse> createCompanyGroup(@RequestParam Long adminId,
                                                                   @RequestBody @Valid CompanyGroupRequest companyGroupRequest) {
        CompanyGroupResponse companyGroupResponse = companyGroupService
                .createCompanyGroup(adminId, companyGroupRequest);

        return new ResponseEntity<>(companyGroupResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyGroupResponse> updateCompanyGroup(@RequestParam Long adminId,
                                                                   @RequestParam Long companyFleetGroupId,
                                                                   @RequestParam Long companyDistrictGroupId,
                                                                   @RequestParam Long companyGroupId,
                                                                   @RequestBody @Valid CompanyGroupUpdateRequest companyGroupUpdateRequest) {
        CompanyGroupResponse companyGroupResponse = companyGroupService
                .updateCompanyGroup(adminId, companyFleetGroupId, companyDistrictGroupId, companyGroupId, companyGroupUpdateRequest);

        return ResponseEntity.ok(companyGroupResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyGroup(@RequestParam Long adminId,
                                                     @RequestParam Long companyFleetGroupId,
                                                     @RequestParam Long companyDistrictGroupId,
                                                     @RequestParam Long companyGroupId
                                                     ) {
        String response = companyGroupService
                .deleteCompany(adminId, companyFleetGroupId, companyDistrictGroupId, companyGroupId);

        return ResponseEntity.ok(response);
    }

}
