package com.mobiliz.service;

import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyConverter;
import com.mobiliz.exception.company.*;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.Company;
import com.mobiliz.repository.CompanyRepository;
import com.mobiliz.request.CompanyRequest;
import com.mobiliz.request.CompanyUpdateRequest;
import com.mobiliz.response.CompanyResponse;
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
    public CompanyResponse createCompany(Long adminId, CompanyRequest companyRequest) {
        if (companyRepository.findByName(companyRequest.getName()).isPresent()){
            throw new CompanyNameInUseException(Messages.Company.NAME_IN_USE + companyRequest.getName());
        }

        if (companyRepository.findByAdminId(adminId).isPresent()) {
            throw new AdminAlreadyHasCompanyException(Messages.Company.ADMIN_IN_USE);
        }

        Company savedCompany = companyConverter.convert(companyRequest);
        savedCompany = companyRepository.save(savedCompany);
        return companyConverter.convert(companyRepository.save(savedCompany));
    }

    public CompanyResponse getCompanyByAdminId(Long adminId) {

        Company foundCompany = findCompanyByAdminId(adminId);
        System.out.println("foundCompany: " + foundCompany);
        return companyConverter.convert(foundCompany);
    }

    @Transactional
    public CompanyResponse updateCompany(Long adminId, CompanyUpdateRequest companyRequest) {

        Company company = companyConverter.update(findCompanyByAdminId(adminId), companyRequest);

        return companyConverter.convert(companyRepository.save(company));
    }

    @Transactional
    public String deleteCompanyByAdminId(Long adminId) {
        Company foundCompany = findCompanyByAdminId(adminId);
        companyRepository.delete(foundCompany);
        return Constants.COMPANY_DELETED;
    }

    public CompanyResponse getCompanyById(Long adminId, Long companyId) {
        Company foundCompanyById  = findByCompanyId(companyId);
        if (!adminId.equals(foundCompanyById.getAdminId())) {
            throw new CompanyIdAndAdminIdNotMatchedException(Messages.Company.ADMIN_NOT_MATCHED + adminId);
        }
        return companyConverter.convert(foundCompanyById);
    }

    public Company findCompanyByAdminId(Long adminId) {

        return companyRepository.findByAdminId(adminId).orElseThrow(
                () -> new AdminNotFoundException(Messages.Company.ADMIN_NOT_EXIST + adminId));
    }

    public Company findByCompanyId(Long id) {

        return companyRepository.findById(id).orElseThrow(
                () -> new CompanyNotFoundException(Messages.Company.NOT_EXISTS + id));
    }


}

