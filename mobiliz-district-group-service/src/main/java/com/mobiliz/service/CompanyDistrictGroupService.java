package com.mobiliz.service;

import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyDistrictGroupConverter;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupNameInUseException;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.model.enums.DistrictStatus;
import com.mobiliz.repository.CompanyDistrictGroupRepository;
import com.mobiliz.request.CompanyDistrictGroupRequest;
import com.mobiliz.request.CompanyDistrictGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import com.mobiliz.response.CompanyFleetDistrictGroupResponse;
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
    private final CompanyGroupClient companyGroupClient;
    Logger logger = LoggerFactory.getLogger(getClass());


    public CompanyDistrictGroupService(CompanyDistrictGroupRepository companyDistrictGroupRepository, CompanyDistrictGroupConverter companyDistrictGroupConverter, JwtTokenService jwtTokenService, CompanyGroupClient companyGroupClient) {
        this.companyDistrictGroupRepository = companyDistrictGroupRepository;
        this.companyDistrictGroupConverter = companyDistrictGroupConverter;
        this.jwtTokenService = jwtTokenService;
        this.companyGroupClient = companyGroupClient;
    }

    public List<CompanyDistrictGroupResponse> getCompanyDistrictGroupsByFleetId(String header) {
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyDistrictGroup> companyDistrictGroups =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        return companyDistrictGroupConverter.convert(companyDistrictGroups);
    }

    public CompanyDistrictGroupResponse getCompanyDistrictGroupsByFleetIdAndDistrictId(
            String header, Long districtGroupId) {

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        logger.info("companyId : {}", companyId);

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByFleetId =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        logger.info("companyDistrictGroupsFoundByFleetId : {}", companyDistrictGroupsFoundByFleetId);

        CompanyDistrictGroup companyDistrictGroup =
                checkByIdAndCompanyIdAndCompanyFleetId(districtGroupId, companyId, companyFleetId);

        logger.info("companyDistrictGroup : {}", companyDistrictGroup);

        return companyDistrictGroupConverter.convert(companyDistrictGroup);
    }

    @Transactional
    public CompanyDistrictGroupResponse createCompanyDistrictGroup(String header,
                                                                   CompanyDistrictGroupRequest companyDistrictGroupRequest) {

        Long companyId = findCompanyIdByHeaderToken(header);
        String companyName = findCompanyNameByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);
        String companyFleetName = findCompanyFleetNameByHeaderToken(header);

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByFleetId =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        logger.info("companyDistrictGroupsFoundByFleetId : {}", companyDistrictGroupsFoundByFleetId);

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
                                                                   Long districtGroupId,
                                                                   CompanyDistrictGroupUpdateRequest companyDistrictGroupUpdateRequest) {
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByFleetId =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        logger.info("companyDistrictGroupsFoundByFleetId : {}", companyDistrictGroupsFoundByFleetId);

        CompanyDistrictGroup companyDistrictGroup =
                checkByIdAndCompanyIdAndCompanyFleetId(districtGroupId, companyId, companyFleetId);

        logger.info("companyDistrictGroup : {}", companyDistrictGroup);


        checkNameAvailable(companyFleetId, companyDistrictGroupUpdateRequest.getName());

        companyDistrictGroup = companyDistrictGroupConverter
                .update(companyDistrictGroup, companyDistrictGroupUpdateRequest);

        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }

    @Transactional
    public String deleteCompanyFleetGroup(String header, Long districtGroupId) {

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByFleetId =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        logger.info("companyDistrictGroupsFoundByFleetId : {}", companyDistrictGroupsFoundByFleetId);

        CompanyDistrictGroup companyDistrictGroup =
                checkByIdAndCompanyIdAndCompanyFleetId(districtGroupId, companyId, companyFleetId);

        logger.info("companyDistrictGroup : {}", companyDistrictGroup);

        companyDistrictGroupRepository.delete(companyDistrictGroup);

        return Constants.COMPANY_DISTRICT_GROUP_DELETED;
    }

    @Transactional
    public VehicleResponseStatus saveCompanyDistrictGroupUser(String header, Long districtGroupId) {

        Long companyId = findCompanyIdByHeaderToken(header);
        Long userId = findUserIdByHeaderToken(header);
        String userName = findUserNameByHeaderToken(header);
        String userSurname = findUserSurNameByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        // kullanıcının company fleet districtleri neler
        // böyle bir fleet district var mı

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByFleetId =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        logger.info("companyDistrictGroupsFoundByFleetId : {}", companyDistrictGroupsFoundByFleetId);

        Optional<CompanyDistrictGroup> companyDistrictGroup =
                companyDistrictGroupRepository.findByIdAndCompanyIdAndCompanyFleetId(
                        districtGroupId, companyId, companyFleetId);

        if (companyDistrictGroup.isEmpty()) {
            logger.warn("Company district group can't found with given id: {} and fleet id: {} ",
                    districtGroupId, companyFleetId);

            return VehicleResponseStatus.DISTRICT_NOT_FOUND;
        }
        logger.info("companyDistrictGroup : {}", companyDistrictGroup.get());


        // varsa kullanımda mı
        // eğer kullanıcı kullanıyorsa already has
        // başka kullanıcı kullanıyorsa already_in_use_someone
        if (DistrictStatus.IN_USE.equals(companyDistrictGroup.get().getStatus())) {
            if (userId.equals(companyDistrictGroup.get().getUserId())) {
                logger.warn("user already has: {} ",  companyDistrictGroup.get());
                return VehicleResponseStatus.USER_ALREADY_HAS;
            }
            logger.warn("not available: {} ",  companyDistrictGroup.get());
            return VehicleResponseStatus.NOT_AVAILABLE;
        }

        // araç kulanımda değilse kullanıcıya ekle

        CompanyDistrictGroup savedCompany = companyDistrictGroupRepository
                .save(saveUserDistrict(userId, userName, userSurname, companyDistrictGroup.get()));
        logger.info("Company District Group user added : {}", savedCompany);
        return VehicleResponseStatus.ADDED;

    }

    private CompanyDistrictGroup saveUserDistrict(Long userId, String userName, String userSurname,
                                                  CompanyDistrictGroup companyDistrictGroup) {
        companyDistrictGroup.setUserId(userId);
        companyDistrictGroup.setFirstName(userName);
        companyDistrictGroup.setSurName(userSurname);
        companyDistrictGroup.setStatus(DistrictStatus.IN_USE);
        return companyDistrictGroup;
    }

    public List<CompanyFleetDistrictGroupResponse> getCompanyFleetDistrictGroupsByFleetId(String header) {
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyDistrictGroup> companyDistrictGroups =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        return  companyDistrictGroupConverter.convertFleetResponses(companyDistrictGroups);
    }


    private List<CompanyDistrictGroup> getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(Long companyId, Long companyFleetGroupId) {
        List<CompanyDistrictGroup> companyDistrictGroups = companyDistrictGroupRepository
                .findAllByCompanyIdAndCompanyFleetId(companyId, companyFleetGroupId)
                .orElseThrow(() ->
                        new CompanyDistrictGroupNotFoundException(
                                Messages.CompanyDistrictGroup.NOT_EXISTS_BY_GIVEN_FLEET_ID + companyFleetGroupId));
        return companyDistrictGroups;
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

    private CompanyDistrictGroup getCompanyDistrictGroups(Long districtGroupId, Long companyFleetId) {
        CompanyDistrictGroup companyDistrictGroup =
                companyDistrictGroupRepository.findAllByIdAndCompanyFleetId(districtGroupId, companyFleetId)
                        .orElseThrow(() ->
                                new CompanyDistrictGroupNotFoundException(
                                        Messages.CompanyDistrictGroup.NOT_EXISTS_BY_GIVEN_ID_AND_FLEET_ID
                                                + districtGroupId));
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

    private String findRoleByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("role").toString();
    }

    private Long findUserIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("userId").toString());
    }

    private String findUserNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("name").toString();
    }

    private String findUserSurNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("surname").toString();
    }


}
