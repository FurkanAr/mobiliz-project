package com.mobiliz.service;

import com.mobiliz.converter.CompanyDistrictGroupConverter;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.repository.CompanyDistrictGroupRepository;
import com.mobiliz.request.CompanyDistrictGroupRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyGroupDistrictService {

    private final CompanyDistrictGroupRepository companyDistrictGroupRepository;
    private final CompanyFleetGroupService companyFleetGroupService;
    private final CompanyDistrictGroupConverter companyDistrictGroupConverter;

    public CompanyGroupDistrictService(CompanyDistrictGroupRepository companyDistrictGroupRepository, CompanyFleetGroupService companyFleetGroupService, CompanyDistrictGroupConverter companyDistrictGroupConverter) {
        this.companyDistrictGroupRepository = companyDistrictGroupRepository;
        this.companyFleetGroupService = companyFleetGroupService;
        this.companyDistrictGroupConverter = companyDistrictGroupConverter;
    }


    public CompanyDistrictGroupResponse createCompanyDistrictGroup(CompanyDistrictGroupRequest companyDistrictGroupRequest){
        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupConverter.convert(companyDistrictGroupRequest);
        companyDistrictGroup  = companyDistrictGroupRepository.save(companyDistrictGroup);
        companyDistrictGroup.setCompanyFleetGroup(companyFleetGroupService
                .getCompanyFleetGroupById(companyDistrictGroupRequest.getCompanyFleetGroupId()));

        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }

    public CompanyDistrictGroup getCompanyDistrictGroupById(Long id){
        return companyDistrictGroupRepository.findById(id).orElseThrow(()
                -> new CompanyDistrictGroupNotFoundException(Messages.CompanyDistrictGroup.NOT_EXISTS + id));
    }

    public List<CompanyDistrictGroup> getCompanyFleetGroupById(Long id) {
        return companyDistrictGroupRepository.findAllByCompanyFleetGroupId(id);
    }
}
