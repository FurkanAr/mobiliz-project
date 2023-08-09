package com.mobiliz.controller;

import com.mobiliz.request.CompanyRequest;
import com.mobiliz.response.company.CompanyCreatedResponse;
import com.mobiliz.response.company.CompanyResponse;
import com.mobiliz.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<String> response() {
        return ResponseEntity.ok("Hello Admin Page");
    }

    @GetMapping("/admins/{adminId}")
    public ResponseEntity<CompanyResponse> getCompanyByAdminId(@PathVariable Long adminId){

        CompanyResponse companyResponse = companyService.getCompanyByAdminId(adminId);

        return ResponseEntity.ok(companyResponse);
    }

    @PostMapping
    public ResponseEntity<CompanyCreatedResponse> createCompany(@RequestBody CompanyRequest companyRequest){

        CompanyCreatedResponse companyResponse = companyService.createCompany(companyRequest);

        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyResponse> updateCompany(@RequestBody CompanyRequest companyRequest){

        CompanyResponse companyResponse = companyService.updateCompany(companyRequest);

        return ResponseEntity.ok(companyResponse);
    }

    @DeleteMapping("{adminId}")
    public ResponseEntity<String> deleteCompanyByAdminId(@PathVariable Long adminId){
        String response = companyService.deleteCompanyByAdminId(adminId);
        return ResponseEntity.ok(response);
    }

}
