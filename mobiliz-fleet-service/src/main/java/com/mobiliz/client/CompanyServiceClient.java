package com.mobiliz.client;

import com.mobiliz.client.response.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "Company", url = "http://localhost:9092/api")
public interface CompanyServiceClient {

    @GetMapping(value = "/companies/{companyId}")
    public CompanyResponse getCompanyByIdAndAdminId(@RequestHeader("Authorization") String header,  @RequestParam Long adminId, @PathVariable Long companyId);

    @GetMapping(value = "/companies")
    public CompanyResponse getCompanyByAdminId(@RequestHeader("Authorization") String header, @RequestParam Long adminId);

}
