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
    public ResponseEntity<CompanyResponse> getCompany(@RequestHeader("Authorization") String header) {

        CompanyResponse companyResponse = companyService.getCompanyById(header);

        return ResponseEntity.ok(companyResponse);
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestHeader("Authorization") String header,
                                                         @RequestBody @Valid CompanyRequest companyRequest) {

        CompanyResponse companyResponse = companyService.createCompany(header, companyRequest);

        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyResponse> updateCompany(@RequestHeader("Authorization") String header,
                                                         @RequestBody @Valid CompanyUpdateRequest companyRequest) {

        CompanyResponse companyResponse = companyService.updateCompany(header, companyRequest);

        return ResponseEntity.ok(companyResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCompanyByAdminId(@RequestHeader("Authorization") String header) {
        String response = companyService.deleteCompanyByAdminId(header);
        return ResponseEntity.ok(response);
    }

}
