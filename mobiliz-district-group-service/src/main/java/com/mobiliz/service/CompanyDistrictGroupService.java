package com.mobiliz.service;

import com.mobiliz.client.CompanyFleetServiceClient;
import com.mobiliz.client.CompanyServiceClient;
import com.mobiliz.client.response.CompanyFleetGroupResponse;
import com.mobiliz.client.response.CompanyResponse;
import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyDistrictGroupConverter;
import com.mobiliz.exception.companyDistrictGroup.*;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.repository.CompanyDistrictGroupRepository;
import com.mobiliz.request.CompanyDistrictGroupRequest;
import com.mobiliz.request.CompanyDistrictGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CompanyDistrictGroupService {

    private final CompanyDistrictGroupRepository companyDistrictGroupRepository;
    private final CompanyFleetServiceClient companyFleetServiceClient;
    private final CompanyServiceClient companyServiceClient;
    private final CompanyDistrictGroupConverter companyDistrictGroupConverter;

    public CompanyDistrictGroupService(CompanyDistrictGroupRepository companyDistrictGroupRepository, CompanyFleetServiceClient companyFleetServiceClient, CompanyServiceClient companyServiceClient, CompanyDistrictGroupConverter companyDistrictGroupConverter) {
        this.companyDistrictGroupRepository = companyDistrictGroupRepository;
        this.companyFleetServiceClient = companyFleetServiceClient;
        this.companyServiceClient = companyServiceClient;
        this.companyDistrictGroupConverter = companyDistrictGroupConverter;
    }

    public List<CompanyDistrictGroupResponse> getCompanyDistrictGroups(String header, Long adminId, Long fleetId) {
        CompanyResponse companyResponse = companyServiceClient.getCompanyByAdminId(header, adminId);

        checkCompanyResponse(adminId, companyResponse);

        CompanyFleetGroupResponse companyFleetGroupResponse = companyFleetServiceClient.getCompanyFleetById(header, adminId, fleetId);

        checkFleetGroupResponse(adminId, companyResponse, companyFleetGroupResponse);

        List<CompanyDistrictGroup> companyDistrictGroups = companyDistrictGroupRepository
                .findAllByCompanyIdAndCompanyFleetId(companyFleetGroupResponse.getCompanyId(), fleetId)
                .orElseThrow(() ->
                        new CompanyFleetGroupIdAndCompanyIdNotMatchedException(
                                Messages.CompanyDistrictGroup.COMPANY_FLEET_GROUP_ID_AND_COMPANY_ID_NOT_MATCHED + fleetId));

        return companyDistrictGroupConverter.convert(companyDistrictGroups);
    }



    @Transactional
    public CompanyDistrictGroupResponse createCompanyDistrictGroup(String header, Long adminId,
                                                                   Long fleetId,
                                                                   CompanyDistrictGroupRequest companyDistrictGroupRequest) {
        CompanyResponse companyResponse = companyServiceClient.getCompanyByAdminId(header, adminId);

        checkCompanyResponse(adminId, companyResponse);

        CompanyFleetGroupResponse companyFleetGroupResponse = companyFleetServiceClient
                .getCompanyFleetById(header, adminId, fleetId);

        checkFleetGroupResponse(adminId, companyResponse, companyFleetGroupResponse);

        checkNameAvailable(companyFleetGroupResponse.getCompanyId(), companyDistrictGroupRequest.getName());

        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupConverter.convert(companyDistrictGroupRequest);

        companyDistrictGroup.setCompanyId(companyFleetGroupResponse.getCompanyId());
        companyDistrictGroup.setCompanyName(companyFleetGroupResponse.getCompanyName());
        companyDistrictGroup.setCompanyFleetId(companyFleetGroupResponse.getId());
        companyDistrictGroup.setCompanyFleetName(companyFleetGroupResponse.getName());

        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }


    @Transactional
    public CompanyDistrictGroupResponse updateCompanyDistrictGroup(String header, Long adminId,
                                                                   Long companyFleetGroupId,
                                                                   Long companyDistrictGroupId,
                                                                   CompanyDistrictGroupUpdateRequest companyDistrictGroupUpdateRequest) {
        CompanyResponse companyResponse = companyServiceClient.getCompanyByAdminId(header, adminId);
        checkCompanyResponse(adminId, companyResponse);


        CompanyFleetGroupResponse companyFleetGroupResponse = companyFleetServiceClient
                .getCompanyFleetById(header, adminId, companyFleetGroupId);

        checkFleetGroupResponse(adminId, companyResponse, companyFleetGroupResponse);

        CompanyDistrictGroup companyDistrictGroupFoundById = getCompanyDistrictGroupById(companyDistrictGroupId);

        if (!companyFleetGroupResponse.getCompanyId().equals(companyDistrictGroupFoundById.getCompanyId())) {
            throw new CompanyDistrictGroupIdAndAdminIdNotMatchedException(
                    Messages.CompanyDistrictGroup.COMPANY_DISTRICT_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED + adminId);
        }
        checkNameAvailable(companyFleetGroupResponse.getCompanyId(), companyDistrictGroupUpdateRequest.getName());

        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupConverter
                .update(companyDistrictGroupFoundById, companyDistrictGroupUpdateRequest);

        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }

    @Transactional
    public String deleteCompanyFleetGroup(String header, Long adminId,
                                          Long companyFleetGroupId, Long companyDistrictGroupId) {

        CompanyResponse companyResponse = companyServiceClient.getCompanyByAdminId(header, adminId);

        checkCompanyResponse(adminId, companyResponse);

        CompanyFleetGroupResponse companyFleetGroupResponse = companyFleetServiceClient
                .getCompanyFleetById(header, adminId, companyFleetGroupId);

        checkFleetGroupResponse(adminId, companyResponse, companyFleetGroupResponse);

        CompanyDistrictGroup companyDistrictGroupFoundById = getCompanyDistrictGroupById(companyDistrictGroupId);

        if (!companyFleetGroupResponse.getCompanyId().equals(companyDistrictGroupFoundById.getCompanyId())) {
            throw new CompanyDistrictGroupIdAndAdminIdNotMatchedException(
                    Messages.CompanyDistrictGroup.COMPANY_DISTRICT_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED + adminId);
        }

        companyDistrictGroupRepository.delete(companyDistrictGroupFoundById);

        return Constants.COMPANY_DISTRICT_GROUP_DELETED;
    }

    public CompanyDistrictGroup getCompanyDistrictGroupById(Long id) {
        return companyDistrictGroupRepository.findById(id).orElseThrow(()
                -> new CompanyDistrictGroupNotFoundException(Messages.CompanyDistrictGroup.NOT_EXISTS + id));
    }

    private void checkNameAvailable(Long companyId, String name) {

        if (companyDistrictGroupRepository.findByNameAndCompanyId(companyId, name).isPresent()) {
            throw new CompanyDistrictGroupNameInUseException(Messages.CompanyDistrictGroup.NAME_IN_USE
                    + name);
        }

    }

    private static void checkFleetGroupResponse(Long adminId, CompanyResponse companyResponse, CompanyFleetGroupResponse companyFleetGroupResponse) {
        if (!companyResponse.getId().equals(companyFleetGroupResponse.getCompanyId())) {
            throw new CompanyFleetGroupIdAndAdminIdNotMatchedException(
                    Messages.CompanyDistrictGroup.COMPANY_FLEET_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED + adminId);
        }
    }

    private static void checkCompanyResponse(Long adminId, CompanyResponse companyResponse) {
        if (companyResponse.getId() == null) {
            throw new AdminNotFoundException(Messages.CompanyDistrictGroup.ADMIN_NOT_FOUND + adminId);
        }
    }


}
