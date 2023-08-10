package com.mobiliz.service;

import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyConverter;
import com.mobiliz.exception.company.AdminAlreadyHasCompanyException;
import com.mobiliz.exception.company.AdminNotFoundException;
import com.mobiliz.exception.company.CompanyNameInUseException;
import com.mobiliz.exception.company.CompanyNotFoundException;
import com.mobiliz.exception.companyFleetGroup.CompanyIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.Company;
import com.mobiliz.repository.CompanyRepository;
import com.mobiliz.request.company.CompanyRequest;
import com.mobiliz.request.company.CompanyUpdateRequest;
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
    public CompanyCreatedResponse createCompany(Long adminId, CompanyRequest companyRequest) {
        if (companyRepository.findByName(companyRequest.getName()).isPresent()){
            throw new CompanyNameInUseException(Messages.Company.NAME_IN_USE + companyRequest.getName());
        }

        if (companyRepository.findByAdminId(adminId).isPresent()) {
            throw new AdminAlreadyHasCompanyException(Messages.Company.ADMIN_IN_USE);
        }

        Company savedCompany = companyConverter.convert(companyRequest);
        savedCompany = companyRepository.save(savedCompany);
        return companyConverter.convertCreatedResponse(companyRepository.save(savedCompany));
    }

    public CompanyResponse getCompanyByAdminId(Long adminId) {

        Company foundCompany = findCompanyByAdminId(adminId);

        return companyConverter.convertToCompanyResponse(foundCompany);
    }

    @Transactional
    public CompanyResponse updateCompany(Long adminId, CompanyUpdateRequest companyRequest) {

        Company company = companyConverter.update(findCompanyByAdminId(adminId), companyRequest);

        return companyConverter.convertToCompanyResponse(company);
    }

    @Transactional
    public String deleteCompanyByAdminId(Long adminId) {
        Company foundCompany = companyRepository.findByAdminId(adminId).orElseThrow(
                () -> new AdminNotFoundException(Messages.Company.ADMIN_NOT_EXIST + adminId));
        companyRepository.delete(foundCompany);
        return Constants.COMPANY_DELETED;
    }

    protected Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new CompanyNotFoundException(Messages.Company.NOT_EXISTS + id));
    }

    public Company findCompanyByAdminId(Long adminId) {

        return companyRepository.findByAdminId(adminId).orElseThrow(
                () -> new AdminNotFoundException(Messages.Company.ADMIN_NOT_EXIST + adminId));
    }

}

