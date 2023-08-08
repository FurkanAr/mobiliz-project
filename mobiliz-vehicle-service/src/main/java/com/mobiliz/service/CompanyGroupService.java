package com.mobiliz.service;

import com.mobiliz.exception.companyGroup.CompanyGroupNotExistException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyGroup;
import com.mobiliz.repository.CompanyGroupRepository;
import com.mobiliz.request.CompanyGroupRequest;
import org.springframework.stereotype.Service;

@Service
public class CompanyGroupService {

    private final CompanyGroupRepository companyGroupRepository;
    private final CompanyGroupDistrictService companyGroupDistrictService;

    public CompanyGroupService(CompanyGroupRepository companyGroupRepository, CompanyGroupDistrictService companyGroupDistrictService) {
        this.companyGroupRepository = companyGroupRepository;
        this.companyGroupDistrictService = companyGroupDistrictService;
    }

    public CompanyGroup createCompanyGroup(CompanyGroupRequest companyGroupRequest){
        CompanyGroup companyGroup = new CompanyGroup();
        companyGroup.setName(companyGroupRequest.getName());
        companyGroup.setCompanyDistrictGroup(companyGroupDistrictService
                .getCompanyDistrictGroupById(companyGroupRequest.getCompanyDistrictGroupId()));
        return companyGroupRepository.save(companyGroup);
    }

    public CompanyGroup getCompanyGroupById(Long id){
        return companyGroupRepository.findById(id).orElseThrow(()
                    ->  new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + id));
    }
}
