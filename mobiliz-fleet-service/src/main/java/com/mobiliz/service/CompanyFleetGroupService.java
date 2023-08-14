package com.mobiliz.service;

import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyFleetGroupConverter;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNameInUseException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.repository.CompanyFleetGroupRepository;
import com.mobiliz.request.CompanyFleetGroupRequest;
import com.mobiliz.request.CompanyFleetUpdateRequest;
import com.mobiliz.response.CompanyFleetGroupResponse;
import com.mobiliz.security.JwtTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyFleetGroupService {

    private final CompanyFleetGroupRepository companyFleetGroupRepository;
    private final CompanyFleetGroupConverter companyFleetGroupConverter;
    private final JwtTokenService jwtTokenService;

    public CompanyFleetGroupService(CompanyFleetGroupRepository companyFleetGroupRepository,  CompanyFleetGroupConverter companyFleetGroupConverter, JwtTokenService jwtTokenService) {
        this.companyFleetGroupRepository = companyFleetGroupRepository;
        this.companyFleetGroupConverter = companyFleetGroupConverter;
        this.jwtTokenService = jwtTokenService;
    }

    public CompanyFleetGroupResponse getCompanyFleetById(String header) {
        Long companyFleetId= findCompanyFleetIdByHeaderToken(header);
        CompanyFleetGroup companyFleetGroup = getCompanyFleetGroupById(companyFleetId);
        return companyFleetGroupConverter.convert(companyFleetGroup);
    }

    public List<CompanyFleetGroupResponse> getCompanyFleetsByCompanyId(String header) {
        Long companyId= findCompanyIdByHeaderToken(header);
        List<CompanyFleetGroup> companyFleetGroups = companyFleetGroupRepository.findAllByCompanyId(companyId);
        return companyFleetGroupConverter.convert(companyFleetGroups);
    }

    @Transactional
    public void createCompanyFleetGroup(CompanyFleetGroupRequest companyFleetGroupRequest) {
        Long companyId = companyFleetGroupRequest.getCompanyId();
        checkNameAvailable(companyId ,companyFleetGroupRequest.getName());
        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter.convert(companyFleetGroupRequest);
        companyFleetGroupRepository.save(companyFleetGroup);
    }

    @Transactional
    public CompanyFleetGroupResponse updateCompanyFleetGroup(String header,
                                                             CompanyFleetUpdateRequest companyFleetUpdateRequest) {

        Long companyFleetId= findCompanyFleetIdByHeaderToken(header);
        Long companyId = findCompanyIdByHeaderToken(header);

        CompanyFleetGroup companyFleetGroupFoundById = getCompanyFleetGroupById(companyFleetId);

        checkNameAvailable(companyId, companyFleetUpdateRequest.getName());

        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter
                .update(companyFleetGroupFoundById, companyFleetUpdateRequest);

        return companyFleetGroupConverter.convert(companyFleetGroupRepository.save(companyFleetGroup));
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
