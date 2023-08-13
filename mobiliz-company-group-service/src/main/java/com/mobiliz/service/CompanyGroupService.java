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
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyGroup;
import com.mobiliz.repository.CompanyGroupRepository;
import com.mobiliz.request.CompanyGroupRequest;
import com.mobiliz.request.CompanyGroupUpdateRequest;
import com.mobiliz.response.CompanyGroupResponse;
import com.mobiliz.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(getClass());

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

    private static CompanyGroup saveCompanyGroupUser(UserCompanyGroupSaveRequest request, CompanyGroup companyGroup) {
        companyGroup.setUserId(request.getUserId());
        companyGroup.setFirstName(request.getUserFirstName());
        companyGroup.setSurName(request.getUserSurName());
        return companyGroup;
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
        System.out.println("saveCompanyGroupUser started");

        Long companyId = findCompanyIdByHeaderToken(header);
        Long userId = findUserIdByHeaderToken(header);
        System.out.println("companyId " + companyId);
        System.out.println("userId " + userId);

        List<CompanyGroup> companyGroupFoundByCompanyId = companyGroupRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS_BY_GIVEN_COMPANY_ID + companyId));
        logger.info("companyGroupFoundByCompanyId : {}", companyGroupFoundByCompanyId);

        CompanyGroup companyGroupFoundById = companyGroupRepository.findById(companyGroupId)
                .orElseThrow(() -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + companyGroupId));
        logger.info("companyGroupFoundById : {}", companyGroupFoundById);

        CompanyGroup companyGroupFoundByIdAndCompanyId = companyGroupRepository
                .findByIdAndCompanyId(companyGroupId, companyId).orElseThrow(
                        () -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS_BY_GIVEN_COMPANY_ID + companyId));
        logger.info("companyGroupFoundByIdAndCompanyId : {}", companyGroupFoundByIdAndCompanyId);
        // ASYA 1 AVRUPA 2  MOBİLİZ -1      ASYA- 3 AVRUPA -4 COMPANY -3
        // ASYA AVRUPA MOBİLİZ              ASYA AVRUPA COMPANY -3 4
        // ASYA MOBİLİZ                     ASYA MOBİLİZ -  1
        // ASYA MOBİLİZ

        // user ın company grubu var mı kontrol et
        Optional<List<CompanyGroup>> companyGroupsFindByUserId = companyGroupRepository.findAllByUserId(userId);
        // grubu varsa hangi gruba istek atıyor kontrol et
        if (companyGroupsFindByUserId.isPresent()) {

            for (CompanyGroup companyGroup : companyGroupsFindByUserId.get()) {
                if (companyGroup.getId().equals(companyGroupId)) {
                    return VehicleResponseStatus.USER_ALREADY_HAS;
                } else {
                    CompanyGroup savedCompany = companyGroupRepository
                            .save(saveCompanyGroupUser(userCompanyGroupSaveRequest, companyGroupFoundByIdAndCompanyId));
                    logger.info("Company Group user added : {}", savedCompany);
                    return VehicleResponseStatus.ADDED;
                }
            }
        }

        // gruplar aynıysa zaten ekli diye return geç
        // değilse kaydet
        CompanyGroup savedCompany = companyGroupRepository
                .save(saveCompanyGroupUser(userCompanyGroupSaveRequest, companyGroupFoundByIdAndCompanyId));
        logger.info("Company Group user added : {}", savedCompany);
        return VehicleResponseStatus.ADDED;
    }

    public List<CompanyGroupResponse> getCompanyGroupsByDistrictGroupId(String header, Long fleetId, Long districtGroupId) {
        System.out.println("getCompanyGroupsByDistrictGroupId started");
        Long companyId = findCompanyIdByHeaderToken(header);

        List<CompanyGroup> companyGroups = companyGroupRepository
                .findAllByCompanyIdCompanyDistrictGroupIdAndCompanyFleetId(companyId, districtGroupId, fleetId)
                .orElseThrow(() -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + districtGroupId));

        System.out.println("-------------------------");
        companyGroups.forEach(c -> System.out.println("getCompanyGroupsByDistrictGroupId companygroups " + c));
        System.out.println("-------------------------");
        System.out.println("getCompanyGroupsByDistrictGroupId finished");

        return companyGroupConverter.convert(companyGroups);
    }

    private Long findUserIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("userId").toString());
    }
}
