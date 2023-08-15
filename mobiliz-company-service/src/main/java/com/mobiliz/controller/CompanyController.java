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
        logger.info("getCompany method started");

        CompanyResponse companyResponse = companyService.getCompanyById(header);
        logger.info("companyResponse : {}", companyResponse);
        logger.info("getCompany method finished");
        return ResponseEntity.ok(companyResponse);
    }

}
