package com.mobiliz.service;

import com.mobiliz.client.request.UserCompanyDistrictGroupSaveRequest;
import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyDistrictGroupConverter;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupNameInUseException;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.repository.CompanyDistrictGroupRepository;
import com.mobiliz.request.CompanyDistrictGroupRequest;
import com.mobiliz.request.CompanyDistrictGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import com.mobiliz.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyDistrictGroupService {

    private final CompanyDistrictGroupRepository companyDistrictGroupRepository;
    private final CompanyDistrictGroupConverter companyDistrictGroupConverter;
    private final JwtTokenService jwtTokenService;
    Logger logger = LoggerFactory.getLogger(getClass());


    public CompanyDistrictGroupService(CompanyDistrictGroupRepository companyDistrictGroupRepository, CompanyDistrictGroupConverter companyDistrictGroupConverter, JwtTokenService jwtTokenService) {
        this.companyDistrictGroupRepository = companyDistrictGroupRepository;
        this.companyDistrictGroupConverter = companyDistrictGroupConverter;
        this.jwtTokenService = jwtTokenService;
    }

    public List<CompanyDistrictGroupResponse> getCompanyDistrictGroupsByFleetId(String header) {
        Long companyFleetGroupId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyDistrictGroup> companyDistrictGroups = companyDistrictGroupRepository
                .findAllByCompanyFleetId(companyFleetGroupId)
                .orElseThrow(() ->
                        new CompanyDistrictGroupNotFoundException(
                                Messages.CompanyDistrictGroup.NOT_EXISTS + companyFleetGroupId));

        return companyDistrictGroupConverter.convert(companyDistrictGroups);
    }

    public CompanyDistrictGroupResponse getCompanyDistrictGroupsByFleetIdAndDistrictId(
            String header, Long fleetId, Long districtGroupId) {
        Long companyId = findCompanyIdByHeaderToken(header);
        logger.info("companyId : {}", companyId);

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByFleetId =
                companyDistrictGroupRepository.findAllByCompanyFleetId(fleetId)
                        .orElseThrow(() ->
                                new CompanyDistrictGroupNotFoundException(
                                        Messages.CompanyDistrictGroup.NOT_EXISTS_BY_GIVEN_FLEET_ID + fleetId));
        logger.info("companyDistrictGroupsFoundByFleetId : {}", companyDistrictGroupsFoundByFleetId);

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByDistrictGroupId =
                companyDistrictGroupRepository.findAllById(districtGroupId)
                        .orElseThrow(() ->
                                new CompanyDistrictGroupNotFoundException(
                                        Messages.CompanyDistrictGroup.NOT_EXISTS + districtGroupId));
        logger.info("companyDistrictGroupsFoundByDistrictGroupId : {}", companyDistrictGroupsFoundByDistrictGroupId);

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByFleetIdAndDistrictGroupId =
                companyDistrictGroupRepository.findAllByIdAndCompanyFleetId(districtGroupId, fleetId)
                        .orElseThrow(() ->
                                new CompanyDistrictGroupNotFoundException(
                                        Messages.CompanyDistrictGroup.NOT_EXISTS_BY_GIVEN_ID_AND_FLEET_ID
                                                + districtGroupId));
        logger.info("companyDistrictGroupsFoundByFleetIdAndDistrictGroupId : {}", companyDistrictGroupsFoundByFleetIdAndDistrictGroupId);

        CompanyDistrictGroup companyDistrictGroup = checkByIdAndCompanyIdAndCompanyFleetId(districtGroupId, companyId, fleetId);

        return companyDistrictGroupConverter.convert(companyDistrictGroup);
    }

    @Transactional
    public CompanyDistrictGroupResponse createCompanyDistrictGroup(String header,
                                                                   CompanyDistrictGroupRequest companyDistrictGroupRequest) {

        Long companyId = findCompanyIdByHeaderToken(header);
        String companyName = findCompanyNameByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);
        String companyFleetName = findCompanyFleetNameByHeaderToken(header);

        checkNameAvailable(companyFleetId, companyDistrictGroupRequest.getName());

        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupConverter
                .convert(companyDistrictGroupRequest);

        companyDistrictGroup.setCompanyId(companyId);
        companyDistrictGroup.setCompanyName(companyName);
        companyDistrictGroup.setCompanyFleetId(companyFleetId);
        companyDistrictGroup.setCompanyFleetName(companyFleetName);

        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }

    @Transactional
    public CompanyDistrictGroupResponse updateCompanyDistrictGroup(String header,
                                                                   Long companyDistrictGroupId,
                                                                   CompanyDistrictGroupUpdateRequest companyDistrictGroupUpdateRequest) {
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        CompanyDistrictGroup companyDistrictGroup = checkByIdAndCompanyIdAndCompanyFleetId(
                companyDistrictGroupId, companyId, companyFleetId);

        checkNameAvailable(companyFleetId, companyDistrictGroupUpdateRequest.getName());

        companyDistrictGroup = companyDistrictGroupConverter
                .update(companyDistrictGroup, companyDistrictGroupUpdateRequest);

        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }

    @Transactional
    public String deleteCompanyFleetGroup(String header, Long companyDistrictGroupId) {

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        CompanyDistrictGroup companyDistrictGroup = checkByIdAndCompanyIdAndCompanyFleetId(
                companyDistrictGroupId, companyId, companyFleetId);

        companyDistrictGroupRepository.delete(companyDistrictGroup);

        return Constants.COMPANY_DISTRICT_GROUP_DELETED;
    }

    @Transactional
    public VehicleResponseStatus saveCompanyDistrictGroupUser(String header, Long districtGroupId, Long fleetId,
                                                              UserCompanyDistrictGroupSaveRequest request) {

        Long companyId = findCompanyIdByHeaderToken(header);


        CompanyDistrictGroup companyDistrictGroupFoundByIdAndCompanyIdAndFleetId = checkByIdAndCompanyIdAndCompanyFleetId(
                districtGroupId, companyId, fleetId);

        Optional<List<CompanyDistrictGroup>> companyDistrictGroupsFoundByUserId = companyDistrictGroupRepository
                .findAllByUserId(request.getUserId());

        if (companyDistrictGroupsFoundByUserId.isPresent()) {

            for (CompanyDistrictGroup companyDistrictGroup : companyDistrictGroupsFoundByUserId.get()) {
                if (companyDistrictGroup.getId().equals(districtGroupId)) {
                    return VehicleResponseStatus.USER_ALREADY_HAS;
                } else {
                    CompanyDistrictGroup savedCompany = companyDistrictGroupRepository
                            .save(saveUser(request, companyDistrictGroupFoundByIdAndCompanyIdAndFleetId));
                    logger.info("Company District Group user added : {}", savedCompany);

                    return VehicleResponseStatus.ADDED;
                }
            }
        }

        // gruplar aynıysa zaten ekli diye return geç
        // değilse kaydet
        CompanyDistrictGroup savedCompany = companyDistrictGroupRepository
                .save(saveUser(request, companyDistrictGroupFoundByIdAndCompanyIdAndFleetId));
        return VehicleResponseStatus.ADDED;

    }

    private CompanyDistrictGroup saveUser(UserCompanyDistrictGroupSaveRequest request,
                                          CompanyDistrictGroup companyDistrictGroup) {
        companyDistrictGroup.setUserId(request.getUserId());
        companyDistrictGroup.setFirstName(request.getUserFirstName());
        companyDistrictGroup.setSurName(request.getUserSurName());
        return companyDistrictGroup;
    }

    private CompanyDistrictGroup checkByIdAndCompanyIdAndCompanyFleetId(Long id, Long companyId, Long companyFleetId) {
        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetId(id, companyId, companyFleetId)
                .orElseThrow(() ->
                        new CompanyDistrictGroupNotFoundException(
                                Messages.CompanyDistrictGroup.NOT_EXISTS + id));
        logger.info("companyDistrictGroup : {}", companyDistrictGroup);
        return companyDistrictGroup;
    }


    private void checkNameAvailable(Long companyFleetId, String name) {

        if (companyDistrictGroupRepository.findByNameAndCompanyFleetId(companyFleetId, name).isPresent()) {
            throw new CompanyDistrictGroupNameInUseException(Messages.CompanyDistrictGroup.NAME_IN_USE
                    + name);
        }

    }

    private Long findCompanyIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
    }

    private Long findCompanyFleetIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyFleetId").toString());
    }

    private String findCompanyNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyName").toString();
    }

    private String findCompanyFleetNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyFleetName").toString();
    }

}
