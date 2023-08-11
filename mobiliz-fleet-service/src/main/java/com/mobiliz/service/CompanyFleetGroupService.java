package com.mobiliz.service;

import com.mobiliz.client.AuthServiceClient;
import com.mobiliz.client.CompanyServiceClient;
import com.mobiliz.client.request.TokenRequest;
import com.mobiliz.client.response.CompanyResponse;
import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyFleetGroupConverter;
import com.mobiliz.exception.companyFleetGroup.AdminNotFoundException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNameInUseException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.repository.CompanyFleetGroupRepository;
import com.mobiliz.request.CompanyFleetGroupRequest;
import com.mobiliz.request.CompanyFleetUpdateRequest;
import com.mobiliz.response.CompanyFleetGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyFleetGroupService {

    private final CompanyFleetGroupRepository companyFleetGroupRepository;
    private final CompanyServiceClient companyService;
    private final CompanyFleetGroupConverter companyFleetGroupConverter;

    public CompanyFleetGroupService(CompanyFleetGroupRepository companyFleetGroupRepository, CompanyServiceClient companyService, CompanyFleetGroupConverter companyFleetGroupConverter) {
        this.companyFleetGroupRepository = companyFleetGroupRepository;
        this.companyService = companyService;
        this.companyFleetGroupConverter = companyFleetGroupConverter;
    }

    public List<CompanyFleetGroupResponse> getCompanyFleetGroups(String header, Long adminId) {
        CompanyResponse companyResponse = companyService.getCompanyByAdminId(header, adminId);

        List<CompanyFleetGroup> companyFleetGroups = companyFleetGroupRepository.findAllByCompanyId(companyResponse.getId())
                .orElseThrow(() ->
                        new CompanyFleetGroupNotFoundException(Messages.CompanyFleetGroup.NOT_EXISTS_BY_GIVEN_ADMIN_ID
                                                + adminId));

        return companyFleetGroupConverter.convert(companyFleetGroups);
    }

    @Transactional
    public CompanyFleetGroupResponse createCompanyFleetGroup(String header,Long adminId,
                                                             CompanyFleetGroupRequest companyFleetGroupRequest) {
        CompanyResponse companyResponse = companyService
                .getCompanyByIdAndAdminId(header, adminId, companyFleetGroupRequest.getCompanyId());

        checkNameAvailable(companyResponse.getId(), companyFleetGroupRequest.getName());

        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter.convert(companyFleetGroupRequest);
        companyFleetGroup.setCompanyId(companyResponse.getId());
        companyFleetGroup.setCompanyName(companyResponse.getName());

        return companyFleetGroupConverter.convert(companyFleetGroupRepository.save(companyFleetGroup));
    }

    @Transactional
    public CompanyFleetGroupResponse updateCompanyFleetGroup(String header,Long adminId, Long companyFleetGroupId,
                                                             CompanyFleetUpdateRequest companyFleetUpdateRequest) {

        CompanyResponse companyResponse = companyService.getCompanyByAdminId(header, adminId);

        CompanyFleetGroup companyFleetGroupFoundById = getCompanyFleetGroupById(companyFleetGroupId);

        if(!companyResponse.getId().equals(companyFleetGroupFoundById.getCompanyId())){
            throw new CompanyFleetGroupIdAndAdminIdNotMatchedException(
                    Messages.CompanyFleetGroup.COMPANY_FLEET_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED + adminId);
        }
        checkNameAvailable(companyResponse.getId(), companyFleetUpdateRequest.getName());

        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter
                .update(companyFleetGroupFoundById, companyFleetUpdateRequest);

        return companyFleetGroupConverter.convert(companyFleetGroupRepository.save(companyFleetGroup));
    }

    @Transactional
    public String deleteCompanyFleetGroup(String header, Long adminId, Long companyFleetGroupId) {
        CompanyResponse companyResponse = companyService.getCompanyByAdminId(header, adminId);
        CompanyFleetGroup companyFleetGroupFoundById = getCompanyFleetGroupById(companyFleetGroupId);

        if(!companyResponse.getId().equals(companyFleetGroupFoundById.getCompanyId())){
            throw new CompanyFleetGroupIdAndAdminIdNotMatchedException(
                    Messages.CompanyFleetGroup.COMPANY_FLEET_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED + adminId);
        }

        companyFleetGroupRepository.delete(companyFleetGroupFoundById);
        return Constants.COMPANY_FLEET_GROUP_DELETED;
    }

    public CompanyFleetGroupResponse getCompanyFleetById(String header, Long adminId, Long fleetId) {
        CompanyResponse companyResponse = companyService.getCompanyByAdminId(header, adminId);

        if(companyResponse.getId() == null){
            throw new AdminNotFoundException(Messages.CompanyFleetGroup.ADMIN_NOT_FOUND + adminId);
        }

        CompanyFleetGroup companyFleetGroup = getCompanyFleetGroupById(fleetId);
        System.out.println("companyResponse: " + companyResponse);
        System.out.println("companyFleetGroup: " + companyFleetGroup);

        if (!companyResponse.getId().equals(companyFleetGroup.getCompanyId())) {
            throw new CompanyFleetGroupIdAndAdminIdNotMatchedException(
                    Messages.CompanyFleetGroup.COMPANY_FLEET_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED + adminId);
        }

        return companyFleetGroupConverter.convert(companyFleetGroup);
    }

    public CompanyFleetGroup getCompanyFleetGroupById(Long id) {
        return companyFleetGroupRepository.findById(id).orElseThrow(()
                -> new CompanyFleetGroupNotFoundException(Messages.CompanyFleetGroup.NOT_EXISTS + id));
    }

    private void checkNameAvailable(Long companyId, String name) {

        if (companyFleetGroupRepository.findByNameAndCompanyId(companyId, name).isPresent()) {
            throw new CompanyFleetGroupNameInUseException(Messages.CompanyFleetGroup.NAME_IN_USE
                    + name);
        }

    }

}
