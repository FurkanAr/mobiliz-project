package com.mobiliz.service;

import com.mobiliz.exception.company.CompanyNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.Company;
import com.mobiliz.repository.CompanyRepository;
import com.mobiliz.request.CompanyRequest;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setAdminId(companyRequest.getAdminId());
        company.setAdminName(companyRequest.getAdminName());
        company.setAdminSurname(companyRequest.getAdminSurname());
        return companyRepository.save(company);
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(Messages.Company.NOT_EXISTS + id));
    }
}
