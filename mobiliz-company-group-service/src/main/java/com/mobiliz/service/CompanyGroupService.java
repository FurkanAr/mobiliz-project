package com.mobiliz.service;

import com.mobiliz.client.CompanyDistrictGroupServiceClient;
import com.mobiliz.client.CompanyFleetServiceClient;
import com.mobiliz.client.request.UserCompanyGroupSaveRequest;
import com.mobiliz.client.response.CompanyDistrictGroupResponse;
import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.constants.Constants;
import com.mobiliz.converter.CompanyGroupConverter;
import com.mobiliz.exception.Permission.UserHasNotPermissionException;
import com.mobiliz.exception.companyGroup.CompanyGroupNameInUseException;
import com.mobiliz.exception.companyGroup.CompanyGroupNotExistException;
import com.mobiliz.exception.companyGroup.CompanyGroupVehiclesInUseException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyGroup;
import com.mobiliz.repository.CompanyGroupRepository;
import com.mobiliz.request.CompanyGroupRequest;
import com.mobiliz.request.CompanyGroupUpdateRequest;
import com.mobiliz.response.CompanyGroupResponse;
import com.mobiliz.security.JwtTokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyGroupService {

    private final CompanyGroupRepository companyGroupRepository;
    private final CompanyGroupConverter companyGroupConverter;
    private final JwtTokenService jwtTokenService;
    private final CompanyFleetServiceClient companyFleetServiceClient;
    private final CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient;

    public CompanyGroupService(CompanyGroupRepository companyGroupRepository, CompanyGroupConverter companyGroupConverter, JwtTokenService jwtTokenService, CompanyFleetServiceClient companyFleetServiceClient, CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient) {
        this.companyGroupRepository = companyGroupRepository;
        this.companyGroupConverter = companyGroupConverter;
        this.jwtTokenService = jwtTokenService;
        this.companyFleetServiceClient = companyFleetServiceClient;
        this.companyDistrictGroupServiceClient = companyDistrictGroupServiceClient;
    }

    private static void checkCompanyDistrictGroupResponse(Long companyId, CompanyDistrictGroupResponse companyDistrictGroupResponse) {
        if (!companyId.equals(companyDistrictGroupResponse.getCompanyId())) {
            throw new UserHasNotPermissionException(Messages.CompanyGroup.USER_HAS_NO_PERMIT);
        }
    }

    public List<CompanyGroupResponse> getCompanyGroupsByDistrictGroupIdAndFleetId(String header,
                                                                                  Long fleetId, Long districtGroupId) {
        Long companyId = findCompanyIdByHeaderToken(header);

        CompanyDistrictGroupResponse companyDistrictGroupResponse = companyDistrictGroupServiceClient
                .getCompanyDistrictGroupsByFleetIdAndDistrictId(header, fleetId, districtGroupId);

        checkCompanyDistrictGroupResponse(companyId, companyDistrictGroupResponse);

        List<CompanyGroup> companyGroups = companyGroupRepository
                .findAllByCompanyIdAndCompanyFleetIdAndCompanyDistrictGroupId(companyId, fleetId, districtGroupId)
                .orElseThrow(() -> new CompanyGroupNotExistException(
                        Messages.CompanyGroup.NOT_EXISTS + companyId));

        return companyGroupConverter.convert(companyGroups);
    }

    public CompanyGroupResponse createCompanyGroup(String header, Long fleetId,
                                                   Long districtGroupId,
                                                   CompanyGroupRequest companyGroupRequest) {

        Long companyId = findCompanyIdByHeaderToken(header);
        String companyName = findCompanyNameByHeaderToken(header);

        CompanyDistrictGroupResponse companyDistrictGroupResponse = companyDistrictGroupServiceClient
                .getCompanyDistrictGroupsByFleetIdAndDistrictId(header, fleetId, districtGroupId);

        checkCompanyDistrictGroupResponse(companyId, companyDistrictGroupResponse);

        checkNameAvailable(companyId, companyGroupRequest.getName());

        CompanyGroup companyGroup = companyGroupConverter.convert(companyGroupRequest);

        companyGroup.setCompanyId(companyId);
        companyGroup.setCompanyName(companyName);
        companyGroup.setCompanyFleetId(companyDistrictGroupResponse.getCompanyFleetGroupId());
        companyGroup.setCompanyFleetName(companyDistrictGroupResponse.getCompanyFleetGroupName());
        companyGroup.setCompanyDistrictGroupId(companyDistrictGroupResponse.getId());
        companyGroup.setCompanyDistrictGroupName(companyDistrictGroupResponse.getName());


        return companyGroupConverter.convert(companyGroupRepository.save(companyGroup));
    }

    public CompanyGroupResponse updateCompanyGroup(String header, Long fleetId,
                                                   Long districtGroupId,
                                                   Long companyGroupId,
                                                   CompanyGroupUpdateRequest companyGroupUpdateRequest) {

        Long companyId = findCompanyIdByHeaderToken(header);

        CompanyGroup companyGroup = companyGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetIdAndCompanyDistrictGroupId(companyGroupId, companyId, fleetId, districtGroupId)
                .orElseThrow(() -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + companyId));

        checkNameAvailable(companyId, companyGroupUpdateRequest.getName());

        companyGroup = companyGroupConverter
                .update(companyGroup, companyGroupUpdateRequest);

        return companyGroupConverter.convert(companyGroupRepository.save(companyGroup));
    }

    public String deleteCompany(String header, Long fleetId,
                                Long districtGroupId, Long companyGroupId) {

        Long companyId = findCompanyIdByHeaderToken(header);

        CompanyGroup companyGroup = companyGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetIdAndCompanyDistrictGroupId(companyGroupId, companyId, fleetId, districtGroupId)
                .orElseThrow(() -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + companyId));

        companyGroupRepository.delete(companyGroup);

        return Constants.COMPANY_GROUP_DELETED;
    }

    private void checkNameAvailable(Long companyId, String name) {

        if (companyGroupRepository.findByNameAndCompanyId(companyId, name).isPresent()) {
            throw new CompanyGroupNameInUseException(Messages.CompanyGroup.NAME_IN_USE
                    + name);
        }
    }

    private Long findCompanyIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
    }

    private String findCompanyNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyName").toString();
    }

    public CompanyGroupResponse getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(
            String header, Long fleetId, Long districtGroupId, Long companyGroupId) {

        Long companyId = findCompanyIdByHeaderToken(header);

        CompanyDistrictGroupResponse companyDistrictGroupResponse = companyDistrictGroupServiceClient
                .getCompanyDistrictGroupsByFleetIdAndDistrictId(header, fleetId, districtGroupId);

        checkCompanyDistrictGroupResponse(companyId, companyDistrictGroupResponse);

        CompanyGroup companyGroup = companyGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetIdAndCompanyDistrictGroupId(companyGroupId, companyId, fleetId, districtGroupId)
                .orElseThrow(() -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + companyId));

        return companyGroupConverter.convert(companyGroup);

    }

    public VehicleResponseStatus saveCompanyGroupUser(String header, Long companyGroupId,
                                                      UserCompanyGroupSaveRequest userCompanyGroupSaveRequest) {

        CompanyGroup companyGroup = companyGroupRepository.findById(companyGroupId).orElseThrow(
                () -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + companyGroupId));

        Optional<CompanyGroup> companyGroupFoundByUserId = companyGroupRepository
                .findByUserId(userCompanyGroupSaveRequest.getUserId());

        if (companyGroupFoundByUserId.isPresent()) {
           return VehicleResponseStatus.REJECTED;
        }

        companyGroup.setUserId(userCompanyGroupSaveRequest.getUserId());
        companyGroup.setFirstName(userCompanyGroupSaveRequest.getUserFirstName());
        companyGroup.setSurName(userCompanyGroupSaveRequest.getUserSurName());
        companyGroupRepository.save(companyGroup);
        return VehicleResponseStatus.ADDED;
    }

    public List<CompanyGroupResponse> getCompanyGroupsByDistrictGroupId(String header, Long districtGroupId) {

        List<CompanyGroup> companyGroups =  companyGroupRepository
                .findAllByCompanyDistrictGroupId(districtGroupId)
                .orElseThrow(() -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + districtGroupId));
        return companyGroupConverter.convert(companyGroups);
    }
}
