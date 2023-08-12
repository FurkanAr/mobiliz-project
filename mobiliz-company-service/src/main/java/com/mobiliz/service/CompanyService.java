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
import com.mobiliz.security.JwtTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;
    private final JwtTokenService tokenService;

    public CompanyService(CompanyRepository companyRepository, CompanyConverter companyConverter, JwtTokenService tokenService) {
        this.companyRepository = companyRepository;
        this.companyConverter = companyConverter;
        this.tokenService = tokenService;
    }

    @Transactional
    public CompanyResponse createCompany(String header, CompanyRequest companyRequest) {
        String token = header.substring(7);
        Long userId = Long.valueOf(tokenService.getClaims(token).get("userId").toString());
        String name = tokenService.getClaims(token).get("name").toString();
        String surname = tokenService.getClaims(token).get("surname").toString();

        if (companyRepository.findByAdminId(userId).isPresent()){
            throw new AdminAlreadyHasCompanyException(Messages.Company.ADMIN_IN_USE + userId);
        }

        if (companyRepository.findByName(companyRequest.getName()).isPresent()){
            throw new CompanyNameInUseException(Messages.Company.NAME_IN_USE + companyRequest.getName());
        }

        Company company = companyConverter.convert(companyRequest);
        company.setAdminId(userId);
        company.setAdminName(name);
        company.setAdminSurname(surname);
        company = companyRepository.save(company);
        return companyConverter.convert(companyRepository.save(company));
    }

    @Transactional
    public CompanyResponse updateCompany(String header, CompanyUpdateRequest companyRequest) {
        Long companyId = findCompanyByHeaderToken(header);

        if (companyRepository.findByName(companyRequest.getName()).isPresent()){
            throw new CompanyNameInUseException(Messages.Company.NAME_IN_USE + companyRequest.getName());
        }

        Company company = companyConverter.update(findByCompanyId(companyId), companyRequest);

        return companyConverter.convert(companyRepository.save(company));
    }

    @Transactional
    public String deleteCompanyByAdminId(String header) {
        Long companyId = findCompanyByHeaderToken(header);
        Company foundCompany = findByCompanyId(companyId);
        companyRepository.delete(foundCompany);
        return Constants.COMPANY_DELETED;
    }

    public CompanyResponse getCompanyById(String header) {
        Long companyId = findCompanyByHeaderToken(header);
        Company foundCompanyById  = findByCompanyId(companyId);
        return companyConverter.convert(foundCompanyById);
    }

    private Company findByCompanyId(Long id) {

        return companyRepository.findById(id).orElseThrow(
                () -> new CompanyNotFoundException(Messages.Company.NOT_EXISTS + id));
    }

    private Long findCompanyByHeaderToken(String header){
        String token = header.substring(7);
        return Long.valueOf(tokenService.getClaims(token).get("companyId").toString());
    }


}

