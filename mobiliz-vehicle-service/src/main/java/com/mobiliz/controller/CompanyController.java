package com.mobiliz.controller;

import com.mobiliz.request.company.CompanyRequest;
import com.mobiliz.request.company.CompanyUpdateRequest;
import com.mobiliz.response.company.CompanyCreatedResponse;
import com.mobiliz.response.company.CompanyResponse;
import com.mobiliz.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<CompanyResponse> getCompanyByAdminId(@RequestParam Long adminId) {

        CompanyResponse companyResponse = companyService.getCompanyByAdminId(adminId);

        return ResponseEntity.ok(companyResponse);
    }

    @PostMapping
    public ResponseEntity<CompanyCreatedResponse> createCompany(@RequestParam Long adminId,
                                                                @RequestBody @Valid CompanyRequest companyRequest) {

        CompanyCreatedResponse companyResponse = companyService.createCompany(adminId, companyRequest);

        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyResponse> updateCompany(@RequestParam Long adminId,
                                                         @RequestBody @Valid CompanyUpdateRequest companyRequest) {

        CompanyResponse companyResponse = companyService.updateCompany(adminId, companyRequest);

        return ResponseEntity.ok(companyResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyByAdminId(@RequestParam Long adminId) {
        String response = companyService.deleteCompanyByAdminId(adminId);
        return ResponseEntity.ok(response);
    }

}
