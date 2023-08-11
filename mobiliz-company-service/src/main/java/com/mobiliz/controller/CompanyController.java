package com.mobiliz.controller;

import com.mobiliz.request.CompanyRequest;
import com.mobiliz.request.CompanyUpdateRequest;
import com.mobiliz.response.CompanyResponse;
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

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyByIdAndAdminId(@RequestParam Long adminId, @PathVariable Long companyId) {

        CompanyResponse companyResponse = companyService.getCompanyById(adminId,companyId);

        return ResponseEntity.ok(companyResponse);
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestParam Long adminId,
                                                                @RequestBody @Valid CompanyRequest companyRequest) {

        CompanyResponse companyResponse = companyService.createCompany(adminId, companyRequest);

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
