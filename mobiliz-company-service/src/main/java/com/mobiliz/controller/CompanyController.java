package com.mobiliz.controller;

import com.mobiliz.response.CompanyResponse;
import com.mobiliz.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

}
