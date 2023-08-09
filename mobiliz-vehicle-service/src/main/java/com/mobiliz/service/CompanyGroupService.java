package com.mobiliz.service;

import com.mobiliz.converter.CompanyGroupConverter;
import com.mobiliz.exception.companyGroup.CompanyGroupNotExistException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyGroup;
import com.mobiliz.repository.CompanyGroupRepository;
import com.mobiliz.request.CompanyGroupRequest;
import com.mobiliz.response.CompanyGroupResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyGroupService {

    private final CompanyGroupRepository companyGroupRepository;
    private final CompanyGroupDistrictService companyGroupDistrictService;
    private final CompanyGroupConverter companyGroupConverter;
    private final CompanyService companyService;
    private final CompanyFleetGroupService companyFleetGroupService;

    public CompanyGroupService(CompanyGroupRepository companyGroupRepository, CompanyGroupDistrictService companyGroupDistrictService, CompanyGroupConverter companyGroupConverter, CompanyService companyService, CompanyFleetGroupService companyFleetGroupService) {
        this.companyGroupRepository = companyGroupRepository;
        this.companyGroupDistrictService = companyGroupDistrictService;
        this.companyGroupConverter = companyGroupConverter;
        this.companyService = companyService;
        this.companyFleetGroupService = companyFleetGroupService;
    }

    public CompanyGroupResponse createCompanyGroup(CompanyGroupRequest companyGroupRequest) {
        CompanyGroup companyGroup = companyGroupConverter.convert(companyGroupRequest);
        companyGroup = companyGroupRepository.save(companyGroup);
        companyGroup.setCompanyFleetGroup(companyFleetGroupService
                .getCompanyFleetGroupById(companyGroupRequest.getCompanyFleetGroupId()));
        companyGroup.setCompanyDistrictGroup(companyGroupDistrictService
                .getCompanyDistrictGroupById(companyGroupRequest.getCompanyDistrictGroupId()));
        companyGroup.setCompany(companyService.getCompanyById(companyGroupRequest.getCompanyId()));

        return companyGroupConverter.convert(companyGroupRepository.save(companyGroup));
    }

    public CompanyGroup getCompanyGroupById(Long id) {
        return companyGroupRepository.findById(id).orElseThrow(()
                -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + id));
    }


    public List<CompanyGroup> getCompanyDistrictGroupById(Long id) {
        return companyGroupRepository.findAllByCompanyDistrictGroupId(id);
    }
}
