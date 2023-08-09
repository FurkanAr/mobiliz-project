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

    public CompanyGroupService(CompanyGroupRepository companyGroupRepository, CompanyGroupDistrictService companyGroupDistrictService, CompanyGroupConverter companyGroupConverter) {
        this.companyGroupRepository = companyGroupRepository;
        this.companyGroupDistrictService = companyGroupDistrictService;
        this.companyGroupConverter = companyGroupConverter;
    }

    public CompanyGroupResponse createCompanyGroup(CompanyGroupRequest companyGroupRequest){
        CompanyGroup companyGroup = companyGroupConverter.convert(companyGroupRequest);
        companyGroup = companyGroupRepository.save(companyGroup);
        companyGroup.setCompanyDistrictGroup(companyGroupDistrictService
                .getCompanyDistrictGroupById(companyGroupRequest.getCompanyDistrictGroupId()));
        return companyGroupConverter.convert(companyGroupRepository.save(companyGroup));
    }

    public CompanyGroup getCompanyGroupById(Long id){
        return companyGroupRepository.findById(id).orElseThrow(()
                    ->  new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + id));
    }


    public List<CompanyGroup> getCompanyDistrictGroupById(Long id) {
        return companyGroupRepository.findAllByCompanyDistrictGroupId(id);
    }
}
