package com.mobiliz.converter;

import com.mobiliz.model.Company;
import com.mobiliz.request.company.CompanyRequest;
import com.mobiliz.request.company.CompanyUpdateRequest;
import com.mobiliz.response.company.CompanyCreatedResponse;
import com.mobiliz.response.company.CompanyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyConverter {

    Logger logger = LoggerFactory.getLogger(getClass());

    public Company convert(CompanyRequest companyRequest){
        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setAdminId(companyRequest.getAdminId());
        company.setAdminName(companyRequest.getAdminName());
        company.setAdminSurname(companyRequest.getAdminSurname());
        return company;
    }

    public CompanyResponse convertToCompanyResponse(Company company){
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        return companyResponse;
    }

    public CompanyCreatedResponse convertCreatedResponse(Company company){
        CompanyCreatedResponse companyResponse = new CompanyCreatedResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        return companyResponse;
    }

    public List<CompanyResponse> convertResponse(List<Company> companies) {
        logger.info("convert companies to companyResponses method started");
        List<CompanyResponse> companyResponses = new ArrayList<>();
        companies.forEach(company -> companyResponses.add(convertToCompanyResponse(company)));
        logger.info("convert companies to companyResponses method successfully worked");
        return companyResponses;
    }

    public Company update(Company company, CompanyUpdateRequest companyRequest) {
        company.setName(companyRequest.getName());
        company.setAdminId(companyRequest.getAdminId());
        company.setAdminName(companyRequest.getAdminName());
        company.setAdminSurname(companyRequest.getAdminSurname());
        return company;
    }
}
