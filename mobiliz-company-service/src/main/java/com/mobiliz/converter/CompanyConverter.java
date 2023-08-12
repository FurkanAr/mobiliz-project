package com.mobiliz.converter;

import com.mobiliz.model.Company;
import com.mobiliz.request.CompanyRequest;
import com.mobiliz.request.CompanyUpdateRequest;
import com.mobiliz.response.CompanyResponse;
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
        return company;
    }

    public CompanyResponse convert(Company company){
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        return companyResponse;
    }

    public List<CompanyResponse> convert(List<Company> companies) {
        logger.info("convert companies to companyResponses method started");
        List<CompanyResponse> companyResponses = new ArrayList<>();
        companies.forEach(company -> companyResponses.add(convert(company)));
        logger.info("convert companies to companyResponses method successfully worked");
        return companyResponses;
    }

    public Company update(Company company, CompanyUpdateRequest companyRequest) {
        company.setName(companyRequest.getName());
        return company;
    }
}
