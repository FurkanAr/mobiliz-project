package com.mobiliz.service;

import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyConverter;
import com.mobiliz.exception.company.AdminAlreadyHasCompanyException;
import com.mobiliz.exception.company.AdminNotFoundException;
import com.mobiliz.exception.company.CompanyNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.Company;
import com.mobiliz.repository.CompanyRepository;
import com.mobiliz.request.CompanyRequest;
import com.mobiliz.response.company.CompanyCreatedResponse;
import com.mobiliz.response.company.CompanyResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    public CompanyService(CompanyRepository companyRepository, CompanyConverter companyConverter) {
        this.companyRepository = companyRepository;
        this.companyConverter = companyConverter;
    }

    @Transactional
    public CompanyCreatedResponse createCompany(CompanyRequest companyRequest) {

        if (companyRepository.findByAdminId(companyRequest.getAdminId()).isPresent()) {
            throw new AdminAlreadyHasCompanyException(Messages.Company.ADMIN_IN_USE);
        }

        Company savedCompany = companyConverter.convert(companyRequest);
        savedCompany = companyRepository.save(savedCompany);
        return companyConverter.convertCreatedResponse(companyRepository.save(savedCompany));
    }

    public CompanyResponse getCompanyByAdminId(Long adminId) {

        Company foundCompany = companyRepository.findByAdminId(adminId).orElseThrow(
                () -> new AdminNotFoundException(Messages.Company.ADMIN_NOT_EXIST + adminId));

        return companyConverter.convertToCompanyResponse(foundCompany);
    }

    @Transactional
    public CompanyResponse updateCompany(CompanyRequest companyRequest) {

        Company foundCompany = companyRepository.findByAdminId(companyRequest.getAdminId()).orElseThrow(
                () -> new AdminNotFoundException(Messages.Company.ADMIN_NOT_EXIST + companyRequest.getAdminId()));

        foundCompany = companyConverter.update(foundCompany, companyRequest);

        return companyConverter.convertToCompanyResponse(foundCompany);
    }

    @Transactional
    public String deleteCompanyByAdminId(Long adminId) {
        Company foundCompany = companyRepository.findByAdminId(adminId).orElseThrow(
                () -> new AdminNotFoundException(Messages.Company.ADMIN_NOT_EXIST + adminId));
        companyRepository.delete(foundCompany);
        return Constants.COMPANY_DELETED;
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new CompanyNotFoundException(Messages.Company.NOT_EXISTS + id));
    }

}

