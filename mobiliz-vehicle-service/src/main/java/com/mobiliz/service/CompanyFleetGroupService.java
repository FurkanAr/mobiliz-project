package com.mobiliz.service;

import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.repository.CompanyFleetGroupRepository;
import com.mobiliz.request.CompanyFleetGroupRequest;
import org.springframework.stereotype.Service;

@Service
public class CompanyFleetGroupService {

    private final CompanyFleetGroupRepository companyFleetGroupRepository;
    private final CompanyService companyService;

    public CompanyFleetGroupService(CompanyFleetGroupRepository companyFleetGroupRepository, CompanyService companyService) {
        this.companyFleetGroupRepository = companyFleetGroupRepository;
        this.companyService = companyService;
    }

    public CompanyFleetGroup createFleet(CompanyFleetGroupRequest companyFleetGroupRequest){
        CompanyFleetGroup companyFleetGroup = new CompanyFleetGroup();
        companyFleetGroup.setCompany(companyService.getCompanyById(companyFleetGroupRequest.getCompanyId()));
        companyFleetGroup.setName(companyFleetGroupRequest.getName());
        return companyFleetGroupRepository.save(companyFleetGroup);
    }

    public CompanyFleetGroup getCompanyFleetGroupById(Long id){
        return companyFleetGroupRepository.findById(id).orElseThrow(()
                -> new CompanyFleetGroupNotFoundException(Messages.CompanyFleetGroup.NOT_EXISTS + id));
    }
}
