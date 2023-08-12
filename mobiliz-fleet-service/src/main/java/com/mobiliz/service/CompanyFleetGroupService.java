package com.mobiliz.service;

import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyFleetGroupConverter;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNameInUseException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.exception.permission.UserHasNotPermissionException;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.repository.CompanyFleetGroupRepository;
import com.mobiliz.request.CompanyFleetGroupRequest;
import com.mobiliz.request.CompanyFleetUpdateRequest;
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

    public CompanyFleetGroupService(CompanyFleetGroupRepository companyFleetGroupRepository,  CompanyFleetGroupConverter companyFleetGroupConverter, JwtTokenService jwtTokenService) {
        this.companyFleetGroupRepository = companyFleetGroupRepository;
        this.companyFleetGroupConverter = companyFleetGroupConverter;
        this.jwtTokenService = jwtTokenService;
    }

    public List<CompanyFleetGroupResponse> getCompanyFleetGroups(String header) {
        Long companyId = findCompanyIdByHeaderToken(header);

        List<CompanyFleetGroup> companyFleetGroups = companyFleetGroupRepository.findAllByCompanyId(companyId)
                .orElseThrow(() ->
                        new CompanyFleetGroupNotFoundException(Messages.CompanyFleetGroup.NOT_EXISTS
                                                + companyId));

        return companyFleetGroupConverter.convert(companyFleetGroups);
    }

    @Transactional
    public CompanyFleetGroupResponse createCompanyFleetGroup(String header,
                                                             CompanyFleetGroupRequest companyFleetGroupRequest) {

        Long companyId = findCompanyIdByHeaderToken(header);
        String companyName = findCompanyNameByHeaderToken(header);

        checkNameAvailable(companyId ,companyFleetGroupRequest.getName());

        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter.convert(companyFleetGroupRequest);
        companyFleetGroup.setCompanyId(companyId);
        companyFleetGroup.setCompanyName(companyName);

        return companyFleetGroupConverter.convert(companyFleetGroupRepository.save(companyFleetGroup));
    }

    @Transactional
    public CompanyFleetGroupResponse updateCompanyFleetGroup(String header,Long companyFleetGroupId,
                                                             CompanyFleetUpdateRequest companyFleetUpdateRequest) {

        Long companyId = findCompanyIdByHeaderToken(header);

        CompanyFleetGroup companyFleetGroupFoundById = getCompanyFleetGroupById(companyFleetGroupId);

        if (!companyId.equals(companyFleetGroupFoundById.getCompanyId())){
            throw new UserHasNotPermissionException(Messages.CompanyFleetGroup.USER_HAS_NO_PERMIT);
        }

        checkNameAvailable(companyId, companyFleetUpdateRequest.getName());

        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter
                .update(companyFleetGroupFoundById, companyFleetUpdateRequest);

        return companyFleetGroupConverter.convert(companyFleetGroupRepository.save(companyFleetGroup));
    }

    @Transactional
    public String deleteCompanyFleetGroup(String header, Long companyFleetGroupId) {
        Long companyId = findCompanyIdByHeaderToken(header);
        CompanyFleetGroup companyFleetGroupFoundById = getCompanyFleetGroupById(companyFleetGroupId);
        if (!companyId.equals(companyFleetGroupFoundById.getCompanyId())){
            throw new UserHasNotPermissionException(Messages.CompanyFleetGroup.USER_HAS_NO_PERMIT);
        }
        companyFleetGroupRepository.delete(companyFleetGroupFoundById);
        return Constants.COMPANY_FLEET_GROUP_DELETED;
    }

    public CompanyFleetGroupResponse getCompanyFleetById(String header, Long fleetId) {
        Long companyId = findCompanyIdByHeaderToken(header);
        CompanyFleetGroup companyFleetGroup = getCompanyFleetGroupById(fleetId);
        if (!companyId.equals(companyFleetGroup.getCompanyId())){
            throw new UserHasNotPermissionException(Messages.CompanyFleetGroup.USER_HAS_NO_PERMIT);
        }
        return companyFleetGroupConverter.convert(companyFleetGroup);
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

    private Long findCompanyIdByHeaderToken(String header){
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
    }

    private String findCompanyNameByHeaderToken(String header){
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyName").toString();
    }

}
