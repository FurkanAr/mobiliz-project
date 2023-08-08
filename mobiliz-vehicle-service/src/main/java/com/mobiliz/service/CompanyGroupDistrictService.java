package com.mobiliz.service;

import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.repository.CompanyDistrictGroupRepository;
import com.mobiliz.request.CompanyDistrictGroupRequest;
import org.springframework.stereotype.Service;

@Service
public class CompanyGroupDistrictService {

    private final CompanyDistrictGroupRepository companyDistrictGroupRepository;
    private final CompanyFleetGroupService companyFleetGroupService;


    public CompanyGroupDistrictService(CompanyDistrictGroupRepository companyDistrictGroupRepository, CompanyFleetGroupService companyFleetGroupService) {
        this.companyDistrictGroupRepository = companyDistrictGroupRepository;
        this.companyFleetGroupService = companyFleetGroupService;
    }

    public CompanyDistrictGroup createCompanyDistrictGroup(CompanyDistrictGroupRequest companyDistrictGroupRequest){
        CompanyDistrictGroup companyDistrictGroup = new CompanyDistrictGroup();
        companyDistrictGroup.setName(companyDistrictGroupRequest.getName());
        companyDistrictGroup.setCompanyFleetGroup(companyFleetGroupService
                .getCompanyFleetGroupById(companyDistrictGroupRequest.getCompanyFleetGroupId()));

        return companyDistrictGroupRepository.save(companyDistrictGroup);
    }

    public CompanyDistrictGroup getCompanyDistrictGroupById(Long id){
        return companyDistrictGroupRepository.findById(id).orElseThrow(()
                -> new CompanyDistrictGroupNotFoundException(Messages.CompanyDistrictGroup.NOT_EXISTS + id));
    }
}
