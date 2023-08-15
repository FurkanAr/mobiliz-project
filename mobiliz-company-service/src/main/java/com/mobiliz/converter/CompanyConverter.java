package com.mobiliz.converter;

import com.mobiliz.model.Company;
import com.mobiliz.request.CompanyRequest;
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
        logger.info("convert method started");

        Company company = new Company();
        company.setName(companyRequest.getName());
        logger.info("convert method finished");

        return company;
    }

    public CompanyResponse convert(Company company){
        logger.info("convert method started");
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        logger.info("convert method finished");
        return companyResponse;
    }

    public List<CompanyResponse> convert(List<Company> companies) {
        logger.info("convert companies to companyResponses method started");
        List<CompanyResponse> companyResponses = new ArrayList<>();
        companies.forEach(company -> companyResponses.add(convert(company)));
        logger.info("convert companies to companyResponses method successfully worked");
        return companyResponses;
    }

}
