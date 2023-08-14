package com.mobiliz.service;

import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyConverter;
import com.mobiliz.exception.company.*;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.Company;
import com.mobiliz.repository.CompanyRepository;
import com.mobiliz.request.CompanyRequest;
import com.mobiliz.response.CompanyResponse;
import com.mobiliz.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;
    private final JwtTokenService tokenService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyService(CompanyRepository companyRepository, CompanyConverter companyConverter, JwtTokenService tokenService) {
        this.companyRepository = companyRepository;
        this.companyConverter = companyConverter;
        this.tokenService = tokenService;
    }

    @Transactional
    public String createCompany(CompanyRequest companyRequest) {
        if (companyRepository.findByName(companyRequest.getName()).isPresent()){
            throw new CompanyNameInUseException(Messages.Company.NAME_IN_USE + companyRequest.getName());
        }

        Company company = companyConverter.convert(companyRequest);
        companyRepository.save(company);
        return Constants.COMPANY_CREATED;
    }

    public CompanyResponse getCompanyById(String header) {
        logger.info("getCompanyById method started");
        Long companyId = findCompanyByHeaderToken(header);
        logger.info("found company id: {}", companyId);
        Company foundCompanyById  = findByCompanyId(companyId);
        logger.info("foundCompanyById: {}", foundCompanyById);
        logger.info("getCompanyById method finished");
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

