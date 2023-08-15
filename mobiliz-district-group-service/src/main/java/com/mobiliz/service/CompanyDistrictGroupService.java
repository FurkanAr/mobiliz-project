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
    Logger logger = LoggerFactory.getLogger(getClass());


    public CompanyDistrictGroupService(CompanyDistrictGroupRepository companyDistrictGroupRepository,
                                       CompanyDistrictGroupConverter companyDistrictGroupConverter,
                                       JwtTokenService jwtTokenService) {
        this.companyDistrictGroupRepository = companyDistrictGroupRepository;
        this.companyDistrictGroupConverter = companyDistrictGroupConverter;
        this.jwtTokenService = jwtTokenService;
    }

    public List<CompanyDistrictGroupResponse> getCompanyDistrictGroupsByFleetId(String header) {
        logger.info("getCompanyDistrictGroupsByFleetId method started");
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyDistrictGroup> companyDistrictGroups =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        logger.info("companyDistrictGroups : {}", companyDistrictGroups);
        logger.info("getCompanyDistrictGroupsByFleetId method finished");
        return companyDistrictGroupConverter.convert(companyDistrictGroups);
    }

    public CompanyDistrictGroupResponse getCompanyDistrictGroupsByFleetIdAndDistrictId(
            String header, Long districtGroupId) {
        logger.info("getCompanyDistrictGroupsByFleetIdAndDistrictId method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        logger.info("companyId : {}", companyId);

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByFleetId =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        logger.info("companyDistrictGroupsFoundByFleetId : {}", companyDistrictGroupsFoundByFleetId);

        CompanyDistrictGroup companyDistrictGroup =
                checkByIdAndCompanyIdAndCompanyFleetId(districtGroupId, companyId, companyFleetId);

        logger.info("companyDistrictGroup : {}", companyDistrictGroup);
        logger.info("getCompanyDistrictGroupsByFleetIdAndDistrictId method finished");
        return companyDistrictGroupConverter.convert(companyDistrictGroup);
    }

    @Transactional
    public CompanyDistrictGroupResponse createCompanyDistrictGroup(String header,
                                                                   CompanyDistrictGroupRequest companyDistrictGroupRequest) {
        logger.info("createCompanyDistrictGroup method started");

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
        companyDistrictGroup  = companyDistrictGroupRepository.save(companyDistrictGroup);
        logger.info("companyDistrictGroup created : {}", companyDistrictGroup);
        logger.info("createCompanyDistrictGroup method finished");
        return companyDistrictGroupConverter.convert(companyDistrictGroup);
    }

    @Transactional
    public CompanyDistrictGroupResponse updateCompanyDistrictGroup(String header,
                                                                   Long districtGroupId,
                                                                   CompanyDistrictGroupUpdateRequest companyDistrictGroupUpdateRequest) {
        logger.info("updateCompanyDistrictGroup method started");

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

        companyDistrictGroup = companyDistrictGroupRepository.save(companyDistrictGroup);
        logger.info("companyDistrictGroup  updated: {}", companyDistrictGroup);

        logger.info("updateCompanyDistrictGroup method finished");
        return companyDistrictGroupConverter.convert(companyDistrictGroup);
    }

    @Transactional
    public String deleteCompanyDistrictGroup(String header, Long districtGroupId) {
        logger.info("deleteCompanyFleetGroup method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyDistrictGroup> companyDistrictGroupsFoundByFleetId =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);

        logger.info("companyDistrictGroupsFoundByFleetId : {}", companyDistrictGroupsFoundByFleetId);

        CompanyDistrictGroup companyDistrictGroup =
                checkByIdAndCompanyIdAndCompanyFleetId(districtGroupId, companyId, companyFleetId);

        logger.info("companyDistrictGroup : {}", companyDistrictGroup);

        companyDistrictGroupRepository.delete(companyDistrictGroup);
        logger.info("companyDistrictGroup deleted : {}", districtGroupId);

        logger.info("deleteCompanyFleetGroup method finished");
        return Constants.COMPANY_DISTRICT_GROUP_DELETED;
    }

    @Transactional
    public VehicleResponseStatus saveCompanyDistrictGroupUser(String header, Long districtGroupId) {
        logger.info("saveCompanyDistrictGroupUser method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long userId = findUserIdByHeaderToken(header);
        String userName = findUserNameByHeaderToken(header);
        String userSurname = findUserSurNameByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

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

        if (DistrictStatus.IN_USE.equals(companyDistrictGroup.get().getStatus())) {
            if (userId.equals(companyDistrictGroup.get().getUserId())) {
                logger.warn("user already has: {} ",  companyDistrictGroup.get());
                return VehicleResponseStatus.USER_ALREADY_HAS;
            }
            logger.warn("not available: {} ",  companyDistrictGroup.get());
            return VehicleResponseStatus.NOT_AVAILABLE;
        }

        CompanyDistrictGroup savedCompany = companyDistrictGroupRepository
                .save(saveUserDistrict(userId, userName, userSurname, companyDistrictGroup.get()));
        logger.info("Company District Group user added : {}", savedCompany);
        logger.info("saveCompanyDistrictGroupUser method finished");
        return VehicleResponseStatus.ADDED;

    }

    private CompanyDistrictGroup saveUserDistrict(Long userId, String userName, String userSurname,
                                                  CompanyDistrictGroup companyDistrictGroup) {
        logger.info("saveUserDistrict method started");

        companyDistrictGroup.setUserId(userId);
        companyDistrictGroup.setFirstName(userName);
        companyDistrictGroup.setSurName(userSurname);
        companyDistrictGroup.setStatus(DistrictStatus.IN_USE);
        logger.info("Company District Group user added : {}", companyDistrictGroup);
        logger.info("saveUserDistrict method finished");
        return companyDistrictGroup;
    }

    public List<CompanyFleetDistrictGroupResponse> getCompanyFleetDistrictGroupsByFleetId(String header) {
        logger.info("getCompanyFleetDistrictGroupsByFleetId method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyDistrictGroup> companyDistrictGroups =
                getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(companyId, companyFleetId);
        logger.info("CompanyDistrictGroups : {}", companyDistrictGroups);
        logger.info("getCompanyFleetDistrictGroupsByFleetId method finished");
        return  companyDistrictGroupConverter.convertFleetResponses(companyDistrictGroups);
    }


    private List<CompanyDistrictGroup> getCompanyDistrictGroupsByCompanyIdAndFleetGroupId(Long companyId, Long companyFleetGroupId) {
        logger.info("getCompanyDistrictGroupsByCompanyIdAndFleetGroupId method started");

        List<CompanyDistrictGroup> companyDistrictGroups = companyDistrictGroupRepository
                .findAllByCompanyIdAndCompanyFleetId(companyId, companyFleetGroupId)
                .orElseThrow(() ->
                        new CompanyDistrictGroupNotFoundException(
                                Messages.CompanyDistrictGroup.NOT_EXISTS_BY_GIVEN_FLEET_ID + companyFleetGroupId));

        logger.info("CompanyDistrictGroups : {}", companyDistrictGroups);
        logger.info("getCompanyDistrictGroupsByCompanyIdAndFleetGroupId method finished");
        return companyDistrictGroups;
    }

    private CompanyDistrictGroup checkByIdAndCompanyIdAndCompanyFleetId(Long id, Long companyId, Long companyFleetId) {
        logger.info("checkByIdAndCompanyIdAndCompanyFleetId method started");
        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetId(id, companyId, companyFleetId)
                .orElseThrow(() ->
                        new CompanyDistrictGroupNotFoundException(
                                Messages.CompanyDistrictGroup.NOT_EXISTS + id));
        logger.info("companyDistrictGroup : {}", companyDistrictGroup);
        logger.info("checkByIdAndCompanyIdAndCompanyFleetId method finished");
        return companyDistrictGroup;
    }

    private void checkNameAvailable(Long companyFleetId, String name) {
        logger.info("checkNameAvailable method started");

        if (companyDistrictGroupRepository.findByNameAndCompanyFleetId(companyFleetId, name).isPresent()) {
            throw new CompanyDistrictGroupNameInUseException(Messages.CompanyDistrictGroup.NAME_IN_USE
                    + name);
        }
        logger.info("checkNameAvailable method finished");

    }

    private Long findCompanyIdByHeaderToken(String header) {
        logger.info("findCompanyIdByHeaderToken method started");
        String token = header.substring(7);
        Long companyId = Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
        logger.info("companyId : {}", companyId);
        logger.info("findCompanyIdByHeaderToken method finished");
        return companyId;
    }

    private Long findCompanyFleetIdByHeaderToken(String header) {
        logger.info("findCompanyFleetIdByHeaderToken method started");
        String token = header.substring(7);
        Long companyFleetId = Long.valueOf(jwtTokenService.getClaims(token).get("companyFleetId").toString());
        logger.info("companyFleetId : {}", companyFleetId);
        logger.info("findCompanyFleetIdByHeaderToken method finished");
        return companyFleetId;
    }

    private String findCompanyNameByHeaderToken(String header) {
        logger.info("findCompanyNameByHeaderToken method started");
        String token = header.substring(7);
        String companyName = jwtTokenService.getClaims(token).get("companyName").toString();
        logger.info("companyName : {}", companyName);
        logger.info("findCompanyNameByHeaderToken method finished");
        return companyName;
    }

    private String findCompanyFleetNameByHeaderToken(String header) {
        logger.info("findCompanyFleetNameByHeaderToken method started");
        String token = header.substring(7);
        String companyFleetName =  jwtTokenService.getClaims(token).get("companyFleetName").toString();
        logger.info("companyFleetName : {}", companyFleetName);
        logger.info("findCompanyFleetNameByHeaderToken method finished");
        return companyFleetName;
    }

    private Long findUserIdByHeaderToken(String header) {
        logger.info("findUserIdByHeaderToken method started");
        String token = header.substring(7);
        Long userId  = Long.valueOf(jwtTokenService.getClaims(token).get("userId").toString());
        logger.info("userId : {}", userId);
        logger.info("findUserIdByHeaderToken method finished");
        return userId;
    }

    private String findUserNameByHeaderToken(String header) {
        logger.info("findUserNameByHeaderToken method started");
        String token = header.substring(7);
        String userName = jwtTokenService.getClaims(token).get("name").toString();
        logger.info("userName : {}", userName);
        logger.info("findUserNameByHeaderToken method finished");
        return userName;
    }

    private String findUserSurNameByHeaderToken(String header) {
        logger.info("findUserSurNameByHeaderToken method started");
        String token = header.substring(7);
        String userSurname = jwtTokenService.getClaims(token).get("surname").toString();
        logger.info("userSurname : {}", userSurname);
        logger.info("findUserSurNameByHeaderToken method finished");
        return userSurname;
    }


}
