package com.mobiliz.service;

import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.constants.Constants;
import com.mobiliz.converter.CompanyGroupConverter;
import com.mobiliz.exception.companyGroup.CompanyGroupNameInUseException;
import com.mobiliz.exception.companyGroup.CompanyGroupNotExistException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyGroup;
import com.mobiliz.model.enums.CompanyGroupStatus;
import com.mobiliz.repository.CompanyGroupRepository;
import com.mobiliz.request.CompanyGroupRequest;
import com.mobiliz.request.CompanyGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictCompanyGroupResponse;
import com.mobiliz.response.CompanyGroupResponse;
import com.mobiliz.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyGroupService {

    private final CompanyGroupRepository companyGroupRepository;
    private final CompanyGroupConverter companyGroupConverter;
    private final JwtTokenService jwtTokenService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyGroupService(CompanyGroupRepository companyGroupRepository, CompanyGroupConverter companyGroupConverter,
                               JwtTokenService jwtTokenService) {
        this.companyGroupRepository = companyGroupRepository;
        this.companyGroupConverter = companyGroupConverter;
        this.jwtTokenService = jwtTokenService;
    }


    public List<CompanyGroupResponse> getCompanyGroupsByCompanyIdAndFleetId(String header) {
        logger.info("getCompanyGroupsByCompanyIdAndFleetId method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetGroupId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyGroup> companyGroups = getCompanyGroupsByCompanyIdAndFleetId(companyId, companyFleetGroupId);

        logger.info("companyGroups : {}", companyGroups);

        logger.info("getCompanyGroupsByCompanyIdAndFleetId method finished");
        return companyGroupConverter.convert(companyGroups);
    }


    public CompanyGroupResponse getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(
            String header, Long districtGroupId, Long companyGroupId) {
        logger.info("getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        logger.info("companyId : {}", companyId);
        logger.info("companyFleetId : {}", companyFleetId);

        List<CompanyGroup> companyGroupsByCompanyIdAndFleetId = getCompanyGroupsByCompanyIdAndFleetId(companyId, companyFleetId);

        logger.info("companyGroupsByCompanyIdAndFleetId : {}", companyGroupsByCompanyIdAndFleetId);

        List<CompanyGroup> companyGroupsByCompanyIdAndFleetIdDistrictGroupId =
                getCompanyGroupsByCompanyIdAndFleetIdAndDistrictGroupId(districtGroupId, companyId, companyFleetId);

        logger.info("companyGroupsByCompanyIdAndFleetIdDistrictGroupId : {}", companyGroupsByCompanyIdAndFleetIdDistrictGroupId);

        CompanyGroup companyGroup = getCompanyGroup(companyGroupId, companyId, companyFleetId, districtGroupId);

        logger.info("companyGroup : {}", companyGroup);

        logger.info("getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId method finished");
        return companyGroupConverter.convert(companyGroup);

    }



    @Transactional
    public CompanyGroupResponse createCompanyGroup(String header,
                                                   Long districtGroupId,
                                                   CompanyGroupRequest companyGroupRequest) {
        logger.info("createCompanyGroup method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        String companyName = findCompanyNameByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);
        String companyFleetName = findCompanyFleetNameByHeaderToken(header);

        checkNameAvailable(companyGroupRequest.getName(), districtGroupId);

        CompanyGroup companyGroup = companyGroupConverter.convert(companyGroupRequest);
        logger.info("companyGroup : {}", companyGroup);

        companyGroup.setCompanyId(companyId);
        companyGroup.setCompanyName(companyName);
        companyGroup.setCompanyFleetId(companyFleetId);
        companyGroup.setCompanyFleetName(companyFleetName);
        companyGroup.setCompanyDistrictGroupId(districtGroupId);
        companyGroup.setCompanyDistrictGroupName(companyGroupRequest.getName());

        companyGroup = companyGroupRepository.save(companyGroup);
        logger.info("companyGroup created: {}", companyGroup);
        logger.info("createCompanyGroup method finished");
        return companyGroupConverter.convert(companyGroup);
    }

    @Transactional
    public CompanyGroupResponse updateCompanyGroup(String header,
                                                   Long districtGroupId,
                                                   Long companyGroupId,
                                                   CompanyGroupUpdateRequest companyGroupUpdateRequest) {
        logger.info("updateCompanyGroup method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        logger.info("companyId : {}", companyId);
        logger.info("companyFleetId : {}", companyFleetId);

        List<CompanyGroup> companyGroupsByCompanyIdAndFleetId = getCompanyGroupsByCompanyIdAndFleetId(companyId, companyFleetId);

        logger.info("companyGroupsByCompanyIdAndFleetId : {}", companyGroupsByCompanyIdAndFleetId);

        List<CompanyGroup> companyGroupsByCompanyIdAndFleetIdDistrictGroupId =
                getCompanyGroupsByCompanyIdAndFleetIdAndDistrictGroupId(districtGroupId, companyId, companyFleetId);

        logger.info("companyGroupsByCompanyIdAndFleetIdDistrictGroupId : {}", companyGroupsByCompanyIdAndFleetIdDistrictGroupId);

        CompanyGroup companyGroup = getCompanyGroup(companyGroupId, companyId, companyFleetId, districtGroupId);

        logger.info("company group : {}", companyGroup);

        checkNameAvailable(companyGroupUpdateRequest.getName(), districtGroupId);

        companyGroup = companyGroupConverter
                .update(companyGroup, companyGroupUpdateRequest);

        logger.info("company group updated: {}", companyGroup);

        companyGroup = companyGroupRepository.save(companyGroup);
        logger.info("company group saved: {}", companyGroup);
        logger.info("updateCompanyGroup method finished");
        return companyGroupConverter.convert(companyGroup);
    }

    @Transactional
    public String deleteCompany(String header,
                                Long districtGroupId, Long companyGroupId) {
        logger.info("deleteCompany method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyGroup> companyGroupsByCompanyIdAndFleetId = getCompanyGroupsByCompanyIdAndFleetId(companyId, companyFleetId);

        logger.info("companyGroupsByCompanyIdAndFleetId : {}", companyGroupsByCompanyIdAndFleetId);

        List<CompanyGroup> companyGroupsByCompanyIdAndFleetIdDistrictGroupId =
                getCompanyGroupsByCompanyIdAndFleetIdAndDistrictGroupId(districtGroupId, companyId, companyFleetId);

        logger.info("companyGroupsByCompanyIdAndFleetIdDistrictGroupId : {}", companyGroupsByCompanyIdAndFleetIdDistrictGroupId);

        CompanyGroup companyGroup = getCompanyGroup(companyGroupId, companyId, companyFleetId, districtGroupId);

        logger.info("company group : {}", companyGroup);

        companyGroupRepository.delete(companyGroup);
        logger.info("company deleted!! id: {}", companyGroupId);
        logger.info("deleteCompany method finished");
        return Constants.COMPANY_GROUP_DELETED;
    }

    @Transactional
    public VehicleResponseStatus saveCompanyGroupUser(String header, Long companyGroupId, Long districtGroupId) {
        logger.info("saveCompanyGroupUser method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long userId = findUserIdByHeaderToken(header);
        String userName = findUserNameByHeaderToken(header);
        String userSurname = findUserSurNameByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyGroup> companyGroupsByCompanyIdAndFleetId = getCompanyGroupsByCompanyIdAndFleetId(companyId, companyFleetId);

        logger.info("companyGroupsByCompanyIdAndFleetId : {}", companyGroupsByCompanyIdAndFleetId);

        List<CompanyGroup> companyGroupsByCompanyIdAndFleetIdDistrictGroupId =
                getCompanyGroupsByCompanyIdAndFleetIdAndDistrictGroupId(districtGroupId, companyId, companyFleetId);

        logger.info("companyGroupsByCompanyIdAndFleetIdDistrictGroupId : {}", companyGroupsByCompanyIdAndFleetIdDistrictGroupId);

        Optional<CompanyGroup> companyGroup = companyGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetIdAndCompanyDistrictGroupId(companyGroupId, companyId, companyFleetId, districtGroupId);

        if (companyGroup.isEmpty()) {
            logger.warn("Company group can't found with given id: {} and fleet id: {} ",
                    companyGroupId, companyFleetId);

            return VehicleResponseStatus.COMPANY_GROUP_NOT_FOUND;
        }
        logger.info("companyGroup : {}", companyGroup.get());

        if (CompanyGroupStatus.IN_USE.equals(companyGroup.get().getStatus())) {
            if (userId.equals(companyGroup.get().getUserId())) {
                logger.warn("user already has: {}", companyGroup.get());
                return VehicleResponseStatus.USER_ALREADY_HAS;
            }
            logger.warn("companyGroup not available : {}", companyGroup.get());
            return VehicleResponseStatus.NOT_AVAILABLE;
        }

        CompanyGroup savedCompany = companyGroupRepository
                .save(saveCompanyGroupUser(userId, userName, userSurname, companyGroup.get()));
        logger.info("Company Group user added : {}", savedCompany);
        logger.info("saveCompanyGroupUser method finished");
        return VehicleResponseStatus.ADDED;
    }

    private CompanyGroup saveCompanyGroupUser(Long userId, String userName, String userSurname, CompanyGroup companyGroup) {
        logger.info("saveCompanyGroupUser method started");
        companyGroup.setUserId(userId);
        companyGroup.setFirstName(userName);
        companyGroup.setSurName(userSurname);
        companyGroup.setStatus(CompanyGroupStatus.IN_USE);
        logger.info("saveCompanyGroupUser method finished");
        return companyGroup;
    }


    public List<CompanyDistrictCompanyGroupResponse> getCompanyGroupsByFleetGroupId(String header, Long districtGroupId) {
        logger.info("getCompanyGroupsByFleetGroupId method started");
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<CompanyGroup> companyGroups =
                getCompanyGroupsByCompanyIdAndFleetIdAndDistrictGroupId(districtGroupId, companyId, companyFleetId);
        logger.info("companyGroups : {}", companyGroups);
        logger.info("getCompanyGroupsByFleetGroupId method finished");
        return companyGroupConverter.convertDistrictCompanyGroupResponses(companyGroups);
    }


    private List<CompanyGroup> getCompanyGroupsByCompanyIdAndFleetId(Long companyId, Long companyFleetGroupId) {
        logger.info("getCompanyGroupsByCompanyIdAndFleetId method started");

        List<CompanyGroup> companyGroups = companyGroupRepository
                .findAllByCompanyIdAndCompanyFleetId(companyId, companyFleetGroupId)
                .orElseThrow(() -> new CompanyGroupNotExistException(
                        Messages.CompanyGroup.NOT_EXISTS_BY_GIVEN_FLEET_ID + companyFleetGroupId));
        logger.info("companyGroups : {}", companyGroups);
        logger.info("getCompanyGroupsByCompanyIdAndFleetId method finished");
        return companyGroups;
    }

    private List<CompanyGroup> getCompanyGroupsByCompanyIdAndFleetIdAndDistrictGroupId(Long districtGroupId,
                                                                                       Long companyId,
                                                                                       Long companyFleetId) {
        logger.info("getCompanyGroupsByCompanyIdAndFleetIdAndDistrictGroupId method started");

        List<CompanyGroup> companyGroupsByCompanyIdAndFleetIdDistrictGroupId = companyGroupRepository
                .findAllByCompanyIdCompanyDistrictGroupIdAndCompanyFleetId(companyId, companyFleetId, districtGroupId)
                .orElseThrow(() -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS_BY_GIVEN_DISTRICT_GROUP_ID + districtGroupId));

        logger.info("companyGroupsByCompanyIdAndFleetIdDistrictGroupId : {}", companyGroupsByCompanyIdAndFleetIdDistrictGroupId);
        logger.info("getCompanyGroupsByCompanyIdAndFleetIdAndDistrictGroupId method finished");
        return companyGroupsByCompanyIdAndFleetIdDistrictGroupId;
    }


    private CompanyGroup getCompanyGroup(Long companyGroupId, Long companyId, Long fleetId, Long districtGroupId) {
        logger.info("getCompanyGroup method started");

        CompanyGroup companyGroup = companyGroupRepository
                .findByIdAndCompanyIdAndCompanyFleetIdAndCompanyDistrictGroupId(companyGroupId, companyId, fleetId, districtGroupId)
                .orElseThrow(() -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + companyGroupId));
        logger.info("companyGroup : {}", companyGroup);
        logger.info("getCompanyGroup method finished");
        return companyGroup;
    }

    private void checkNameAvailable(String name, Long companyDistrictGroupId) {
        logger.info("checkNameAvailable method started");
        if (companyGroupRepository.findByNameAndCompanyDistrictGroupId(name, companyDistrictGroupId).isPresent()) {
            logger.warn("name not available : {}", name);
            throw new CompanyGroupNameInUseException(Messages.CompanyGroup.NAME_IN_USE
                    + name);
        }
        logger.info("checkNameAvailable method finished");

    }

    private Long findUserIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("userId").toString());
    }

    private Long findCompanyFleetIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyFleetId").toString());
    }

    private Long findCompanyIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
    }

    private String findCompanyNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyName").toString();
    }

    private String findCompanyFleetNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyFleetName").toString();
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
