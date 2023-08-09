package com.mobiliz.service;

import com.mobiliz.converter.CompanyConverter;
import com.mobiliz.exception.company.CompanyNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.Company;
import com.mobiliz.repository.CompanyRepository;
import com.mobiliz.request.CompanyRequest;
import com.mobiliz.response.CompanyResponse;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    public CompanyService(CompanyRepository companyRepository, CompanyConverter companyConverter) {
        this.companyRepository = companyRepository;
        this.companyConverter = companyConverter;
    }

    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        Company company = companyConverter.convert(companyRequest);
        company = companyRepository.save(company);
        return companyConverter.convert(companyRepository.save(company));
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(Messages.Company.NOT_EXISTS + id));
    }
}
