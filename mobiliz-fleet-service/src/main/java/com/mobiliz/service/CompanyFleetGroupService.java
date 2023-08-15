package com.mobiliz.service;

import com.mobiliz.client.CompanyDistrictGroupServiceClient;
import com.mobiliz.client.response.CompanyFleetDistrictGroupResponse;
import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyFleetGroupConverter;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNameInUseException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.repository.CompanyFleetGroupRepository;
import com.mobiliz.request.CompanyFleetGroupRequest;
import com.mobiliz.response.CompanyFleetDistrictsGroupResponse;
import com.mobiliz.response.CompanyFleetGroupResponse;
import com.mobiliz.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyFleetGroupService {

    private final CompanyFleetGroupRepository companyFleetGroupRepository;
    private final CompanyFleetGroupConverter companyFleetGroupConverter;
    private final JwtTokenService jwtTokenService;
    private final CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient;
    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyFleetGroupService(CompanyFleetGroupRepository companyFleetGroupRepository, CompanyFleetGroupConverter companyFleetGroupConverter, JwtTokenService jwtTokenService, CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient) {
        this.companyFleetGroupRepository = companyFleetGroupRepository;
        this.companyFleetGroupConverter = companyFleetGroupConverter;
        this.jwtTokenService = jwtTokenService;
        this.companyDistrictGroupServiceClient = companyDistrictGroupServiceClient;
    }

    public CompanyFleetDistrictsGroupResponse getCompanyFleetById(String header) {
        logger.info("getCompanyFleetById method started");
        Long companyFleetId= findCompanyFleetIdByHeaderToken(header);
        CompanyFleetGroup companyFleetGroup = getCompanyFleetGroupById(companyFleetId);
        List<CompanyFleetDistrictGroupResponse> response = companyDistrictGroupServiceClient
                .getCompanyFleetDistrictGroupsByFleetId(header);
        logger.info("response : {}", response);
        logger.info("getCompanyFleetById method finished");
        return companyFleetGroupConverter.convertCompanyFleetDistrictsGroupResponse(companyFleetGroup, response);
    }

    public CompanyFleetGroupResponse getCompanyFleetsByFleetId(String header) {
        logger.info("getCompanyFleetsByFleetId method started");
        Long companyFleetId= findCompanyFleetIdByHeaderToken(header);
        CompanyFleetGroup companyFleetGroup = getCompanyFleetGroupById(companyFleetId);
        logger.info("companyFleetGroup : {}", companyFleetGroup);
        logger.info("getCompanyFleetsByFleetId method finished");
        return companyFleetGroupConverter.convert(companyFleetGroup);
    }

    @Transactional
    public void createCompanyFleetGroup(CompanyFleetGroupRequest companyFleetGroupRequest) {
        logger.info("createCompanyFleetGroup method started");
        Long companyId = companyFleetGroupRequest.getCompanyId();
        checkNameAvailable(companyId ,companyFleetGroupRequest.getName());
        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter.convert(companyFleetGroupRequest);
        logger.info("companyFleetGroup : {}", companyFleetGroup);
        logger.info("createCompanyFleetGroup method finished");
        companyFleetGroupRepository.save(companyFleetGroup);
    }

    @Transactional
    public String deleteCompanyFleetGroup(String header) {
        logger.info("deleteCompanyFleetGroup method started");
        Long companyFleetId= findCompanyFleetIdByHeaderToken(header);
        CompanyFleetGroup companyFleetGroupFoundById = getCompanyFleetGroupById(companyFleetId);
        companyFleetGroupRepository.delete(companyFleetGroupFoundById);
        logger.info("companyFleetGroup : {}", companyFleetId);
        logger.info("deleteCompanyFleetGroup method finished");
        return Constants.COMPANY_FLEET_GROUP_DELETED;
    }


    public CompanyFleetGroup getCompanyFleetGroupById(Long id) {
        logger.info("getCompanyFleetGroupById method started");
        CompanyFleetGroup companyFleetGroup =companyFleetGroupRepository.findById(id).orElseThrow(()
                -> new CompanyFleetGroupNotFoundException(Messages.CompanyFleetGroup.NOT_EXISTS + id));
        logger.info("companyFleetGroup : {}", companyFleetGroup);
        logger.info("getCompanyFleetGroupById method finished");
        return companyFleetGroup;
    }

    private void checkNameAvailable(Long companyId, String name) {
        logger.info("checkNameAvailable method started");

        if (companyFleetGroupRepository.findByNameAndCompanyId(companyId, name).isPresent()) {
            throw new CompanyFleetGroupNameInUseException(Messages.CompanyFleetGroup.NAME_IN_USE
                    + name);
        }
        logger.info("checkNameAvailable method finished");

    }

    private Long findCompanyFleetIdByHeaderToken(String header) {
        logger.info("findCompanyFleetIdByHeaderToken method started");
        String token = header.substring(7);
        Long companyFleetId = Long.valueOf(jwtTokenService.getClaims(token).get("companyFleetId").toString());
        logger.info("companyFleetId : {}", companyFleetId);
        logger.info("findCompanyFleetIdByHeaderToken method finished");
        return companyFleetId;
    }





}
