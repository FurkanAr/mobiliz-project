package com.mobiliz.service;

import com.mobiliz.client.CompanyFleetServiceClient;
import com.mobiliz.client.CompanyServiceClient;
import com.mobiliz.client.request.UserCompanyDistrictGroupSaveRequest;
import com.mobiliz.client.response.CompanyFleetGroupResponse;
import com.mobiliz.client.response.CompanyResponse;
import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyDistrictGroupConverter;
import com.mobiliz.exception.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.companyDistrictGroup.*;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.exception.permission.UserHasNotPermissionException;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.repository.CompanyDistrictGroupRepository;
import com.mobiliz.request.CompanyDistrictGroupRequest;
import com.mobiliz.request.CompanyDistrictGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import com.mobiliz.security.JwtTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyDistrictGroupService {

    private final CompanyDistrictGroupRepository companyDistrictGroupRepository;
    private final CompanyFleetServiceClient companyFleetServiceClient;
    private final CompanyDistrictGroupConverter companyDistrictGroupConverter;
    private final JwtTokenService jwtTokenService;

    public CompanyDistrictGroupService(CompanyDistrictGroupRepository companyDistrictGroupRepository, CompanyFleetServiceClient companyFleetServiceClient, CompanyDistrictGroupConverter companyDistrictGroupConverter, JwtTokenService jwtTokenService) {
        this.companyDistrictGroupRepository = companyDistrictGroupRepository;
        this.companyFleetServiceClient = companyFleetServiceClient;
        this.companyDistrictGroupConverter = companyDistrictGroupConverter;
        this.jwtTokenService = jwtTokenService;
    }

    public List<CompanyDistrictGroupResponse> getCompanyDistrictGroupsByFleetId(String header, Long fleetId) {
        Long companyId = findCompanyIdByHeaderToken(header);

        CompanyFleetGroupResponse companyFleetGroupResponse  =companyFleetServiceClient
                .getCompanyFleetById(header, fleetId);

        checkFleetGroupResponse(companyId, companyFleetGroupResponse);

        List<CompanyDistrictGroup> companyDistrictGroups = companyDistrictGroupRepository
                .findAllByCompanyIdAndCompanyFleetId(companyId, fleetId)
                .orElseThrow(() ->
                        new CompanyDistrictGroupNotFoundException(
                                Messages.CompanyDistrictGroup.NOT_EXISTS + companyId));

        return companyDistrictGroupConverter.convert(companyDistrictGroups);
    }

    public CompanyDistrictGroupResponse getCompanyDistrictGroupsByFleetIdAndDistrictId(
            String header, Long fleetId, Long districtGroupId) {

        Long companyId = findCompanyIdByHeaderToken(header);


        CompanyFleetGroupResponse companyFleetGroupResponse  =companyFleetServiceClient
                .getCompanyFleetById(header, fleetId);

        checkFleetGroupResponse(companyId, companyFleetGroupResponse);

        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetId(districtGroupId, companyId, fleetId)
                .orElseThrow(() ->
                        new CompanyDistrictGroupNotFoundException(
                                Messages.CompanyDistrictGroup.NOT_EXISTS + companyId));

        return companyDistrictGroupConverter.convert(companyDistrictGroup);

    }


    @Transactional
    public CompanyDistrictGroupResponse createCompanyDistrictGroup(String header, Long fleetId,
                                                                   CompanyDistrictGroupRequest companyDistrictGroupRequest) {
        Long companyId = findCompanyIdByHeaderToken(header);
        String companyName = findCompanyNameByHeaderToken(header);

        CompanyFleetGroupResponse companyFleetGroupResponse  =companyFleetServiceClient
                .getCompanyFleetById(header, fleetId);

        checkFleetGroupResponse(companyId, companyFleetGroupResponse);

        checkNameAvailable(companyId ,companyDistrictGroupRequest.getName());

        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupConverter
                .convert(companyDistrictGroupRequest);

        companyDistrictGroup.setCompanyId(companyId);
        companyDistrictGroup.setCompanyName(companyName);
        companyDistrictGroup.setCompanyFleetId(companyFleetGroupResponse.getId());
        companyDistrictGroup.setCompanyFleetName(companyFleetGroupResponse.getName());

        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }


    @Transactional
    public CompanyDistrictGroupResponse updateCompanyDistrictGroup(String header,
                                                                   Long companyFleetGroupId,
                                                                   Long companyDistrictGroupId,
                                                                   CompanyDistrictGroupUpdateRequest companyDistrictGroupUpdateRequest) {
        Long companyId = findCompanyIdByHeaderToken(header);

        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetId(companyDistrictGroupId, companyId, companyFleetGroupId)
                .orElseThrow(() ->
                        new CompanyDistrictGroupNotFoundException(
                                Messages.CompanyDistrictGroup.NOT_EXISTS + companyId));

        checkNameAvailable(companyId, companyDistrictGroupUpdateRequest.getName());

        companyDistrictGroup = companyDistrictGroupConverter
                .update(companyDistrictGroup, companyDistrictGroupUpdateRequest);

        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }

    @Transactional
    public String deleteCompanyFleetGroup(String header, Long companyFleetGroupId, Long companyDistrictGroupId) {

        Long companyId = findCompanyIdByHeaderToken(header);

        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetId(companyDistrictGroupId, companyId, companyFleetGroupId)
                .orElseThrow(() ->
                        new CompanyDistrictGroupNotFoundException(
                                Messages.CompanyDistrictGroup.NOT_EXISTS + companyId));

        companyDistrictGroupRepository.delete(companyDistrictGroup);

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

    private static void checkFleetGroupResponse(Long companyId, CompanyFleetGroupResponse companyFleetGroupResponse) {
        if (!companyId.equals(companyFleetGroupResponse.getCompanyId())) {
            throw new UserHasNotPermissionException(Messages.CompanyDistrictGroup.USER_HAS_NO_PERMIT);
        }
    }

    private static void checkCompanyResponse(Long adminId, CompanyResponse companyResponse) {
        if (companyResponse.getId() == null) {
            throw new AdminNotFoundException(Messages.CompanyDistrictGroup.ADMIN_NOT_FOUND + adminId);
        }
    }

    private Long findCompanyIdByHeaderToken(String header){
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
    }

    private String findCompanyNameByHeaderToken(String header){
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyName").toString();
    }


    public VehicleResponseStatus saveCompanyDistrictGroupUser(String header, Long districtGroupId,
                                                              UserCompanyDistrictGroupSaveRequest request) {

        CompanyDistrictGroup companyDistrictGroup = getCompanyDistrictGroupById(districtGroupId);

        Optional<CompanyDistrictGroup> companyDistrictGroupFoundByUserId = companyDistrictGroupRepository
                .findByUserId(request.getUserId());

        if (companyDistrictGroupFoundByUserId.isPresent()){
            return VehicleResponseStatus.REJECTED;
        }

        companyDistrictGroup.setUserId(request.getUserId());
        companyDistrictGroup.setFirstName(request.getUserFirstName());
        companyDistrictGroup.setSurName(request.getUserSurName());
        companyDistrictGroupRepository.save(companyDistrictGroup);
        return VehicleResponseStatus.ADDED;
    }
}
