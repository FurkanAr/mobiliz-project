package com.mobiliz.service;

import com.mobiliz.converter.CompanyFleetGroupConverter;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.repository.CompanyFleetGroupRepository;
import com.mobiliz.request.CompanyFleetGroupRequest;
import com.mobiliz.response.CompanyFleetGroupResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyFleetGroupService {

    private final CompanyFleetGroupRepository companyFleetGroupRepository;
    private final CompanyService companyService;
    private final CompanyFleetGroupConverter companyFleetGroupConverter;

    public CompanyFleetGroupService(CompanyFleetGroupRepository companyFleetGroupRepository, CompanyService companyService, CompanyFleetGroupConverter companyFleetGroupConverter) {
        this.companyFleetGroupRepository = companyFleetGroupRepository;
        this.companyService = companyService;
        this.companyFleetGroupConverter = companyFleetGroupConverter;
    }

    public CompanyFleetGroupResponse createFleet(CompanyFleetGroupRequest companyFleetGroupRequest) {
        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter.convert(companyFleetGroupRequest);
        companyFleetGroup = companyFleetGroupRepository.save(companyFleetGroup);
        companyFleetGroup.setCompany(companyService.getCompanyById(companyFleetGroupRequest.getCompanyId()));
        return companyFleetGroupConverter.convert(companyFleetGroupRepository.save(companyFleetGroup));
    }

    public CompanyFleetGroup getCompanyFleetGroupById(Long id) {
        return companyFleetGroupRepository.findById(id).orElseThrow(()
                -> new CompanyFleetGroupNotFoundException(Messages.CompanyFleetGroup.NOT_EXISTS + id));
    }

    public List<CompanyFleetGroup> getCompanyById(Long id) {
        return companyFleetGroupRepository.findAllByCompanyId(id);
    }
}
