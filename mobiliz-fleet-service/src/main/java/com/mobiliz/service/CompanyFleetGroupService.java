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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyFleetGroupService {

    private final CompanyFleetGroupRepository companyFleetGroupRepository;
    private final CompanyFleetGroupConverter companyFleetGroupConverter;
    private final JwtTokenService jwtTokenService;
    private final CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient;

    public CompanyFleetGroupService(CompanyFleetGroupRepository companyFleetGroupRepository, CompanyFleetGroupConverter companyFleetGroupConverter, JwtTokenService jwtTokenService, CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient) {
        this.companyFleetGroupRepository = companyFleetGroupRepository;
        this.companyFleetGroupConverter = companyFleetGroupConverter;
        this.jwtTokenService = jwtTokenService;
        this.companyDistrictGroupServiceClient = companyDistrictGroupServiceClient;
    }

    public CompanyFleetDistrictsGroupResponse getCompanyFleetById(String header) {
        Long companyFleetId= findCompanyFleetIdByHeaderToken(header);
        CompanyFleetGroup companyFleetGroup = getCompanyFleetGroupById(companyFleetId);
        List<CompanyFleetDistrictGroupResponse> response = companyDistrictGroupServiceClient
                .getCompanyFleetDistrictGroupsByFleetId(header);
        return companyFleetGroupConverter.convertCompanyFleetDistrictsGroupResponse(companyFleetGroup, response);
    }

    public CompanyFleetGroupResponse getCompanyFleetsByFleetId(String header) {
        Long companyId= findCompanyIdByHeaderToken(header);
        Long companyFleetId= findCompanyFleetIdByHeaderToken(header);
        CompanyFleetGroup companyFleetGroup = getCompanyFleetGroupById(companyFleetId);
        return companyFleetGroupConverter.convert(companyFleetGroup);
    }

    @Transactional
    public void createCompanyFleetGroup(CompanyFleetGroupRequest companyFleetGroupRequest) {
        Long companyId = companyFleetGroupRequest.getCompanyId();
        checkNameAvailable(companyId ,companyFleetGroupRequest.getName());
        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter.convert(companyFleetGroupRequest);
        companyFleetGroupRepository.save(companyFleetGroup);
    }

    @Transactional
    public String deleteCompanyFleetGroup(String header) {
        Long companyFleetId= findCompanyFleetIdByHeaderToken(header);
        CompanyFleetGroup companyFleetGroupFoundById = getCompanyFleetGroupById(companyFleetId);
        companyFleetGroupRepository.delete(companyFleetGroupFoundById);
        return Constants.COMPANY_FLEET_GROUP_DELETED;
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

    private Long findCompanyFleetIdByHeaderToken(String header){
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyFleetId").toString());
    }

    private Long findCompanyIdByHeaderToken(String header){
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
    }


}
