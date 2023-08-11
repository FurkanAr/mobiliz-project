package com.mobiliz.service;

import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyGroupConverter;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNameInUseException;
import com.mobiliz.exception.companyFleetGroup.CompanyIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyGroup.CompanyGroupNotExistException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.Company;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.model.CompanyGroup;
import com.mobiliz.repository.CompanyGroupRepository;
import com.mobiliz.request.companyGroup.CompanyGroupRequest;
import com.mobiliz.request.companyGroup.CompanyGroupUpdateRequest;
import com.mobiliz.response.CompanyGroupResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mobiliz.utils.CompanyMatch.*;

@Service
public class CompanyGroupService {

    private final CompanyGroupRepository companyGroupRepository;
    private final CompanyDistrictGroupService companyGroupDistrictService;
    private final CompanyGroupConverter companyGroupConverter;
    private final CompanyService companyService;
    private final CompanyFleetGroupService companyFleetGroupService;

    public CompanyGroupService(CompanyGroupRepository companyGroupRepository, CompanyDistrictGroupService companyGroupDistrictService, CompanyGroupConverter companyGroupConverter, CompanyService companyService, CompanyFleetGroupService companyFleetGroupService) {
        this.companyGroupRepository = companyGroupRepository;
        this.companyGroupDistrictService = companyGroupDistrictService;
        this.companyGroupConverter = companyGroupConverter;
        this.companyService = companyService;
        this.companyFleetGroupService = companyFleetGroupService;
    }


    public List<CompanyGroupResponse> getCompanyGroups(Long adminId) {
        Company company = companyService.findCompanyByAdminId(adminId);

        List<CompanyGroup> companyGroups = companyGroupRepository.findAllByCompanyId(company.getId())
                .orElseThrow(() -> new CompanyGroupNotExistException(
                        Messages.CompanyGroup.NOT_EXISTS_BY_GIVEN_COMPANY_ID + company.getId()));

        return companyGroupConverter.convert(companyGroups);
    }

    public CompanyGroupResponse createCompanyGroup(Long adminId, CompanyGroupRequest companyGroupRequest) {
        Company company = companyService.getCompanyById(companyGroupRequest.getCompanyId());

        checkAdminIdAndCompanyAdminIdMatch(adminId, company);

        CompanyFleetGroup companyFleetGroup = companyFleetGroupService
                .getCompanyFleetGroupById(companyGroupRequest.getCompanyFleetGroupId());

        CompanyDistrictGroup companyDistrictGroup = companyGroupDistrictService
                .getCompanyDistrictGroupById(companyGroupRequest.getCompanyDistrictGroupId());

        checkCompanyDistrictGroupAndAdminMatch(companyDistrictGroup, company);
        checkCompanyFleetGroupAndDistrictGroupMatch(companyDistrictGroup, companyFleetGroup);
        checkCompanyFleetGroupAndAdminMatch(companyFleetGroup, company);

        checkNameAvailable(company, companyGroupRequest.getName());

        CompanyGroup companyGroup = companyGroupConverter.convert(companyGroupRequest);

        companyGroup = companyGroupRepository.save(companyGroup);

        companyGroup.setCompanyFleetGroup(companyFleetGroup);
        companyGroup.setCompanyDistrictGroup(companyDistrictGroup);
        companyGroup.setCompany(company);

        return companyGroupConverter.convert(companyGroupRepository.save(companyGroup));
    }

    public CompanyGroupResponse updateCompanyGroup(Long adminId, Long companyFleetGroupId,
                                                   Long companyDistrictGroupId, Long companyGroupId,
                                                   CompanyGroupUpdateRequest companyGroupUpdateRequest) {

        Company companyFoundByAdminId = companyService.findCompanyByAdminId(adminId);
        CompanyFleetGroup companyFleetGroupFoundById = companyFleetGroupService
                .getCompanyFleetGroupById(companyFleetGroupId);
        CompanyDistrictGroup companyDistrictGroupFoundById = companyGroupDistrictService
                .getCompanyDistrictGroupById(companyDistrictGroupId);

        CompanyGroup companyGroupFoundById = getCompanyGroupById(companyGroupId);

        checkCompanyGroupAndAdminMatch(companyGroupFoundById, companyFoundByAdminId);
        checkCompanyDistrictGroupAndGroupMatch(companyDistrictGroupFoundById, companyGroupFoundById);
        checkCompanyDistrictGroupAndAdminMatch(companyDistrictGroupFoundById, companyFoundByAdminId);
        checkCompanyFleetGroupAndDistrictGroupMatch(companyDistrictGroupFoundById, companyFleetGroupFoundById);
        checkCompanyFleetGroupAndAdminMatch(companyFleetGroupFoundById, companyFoundByAdminId);

        checkNameAvailable(companyFoundByAdminId, companyGroupUpdateRequest.getName());

        CompanyGroup companyGroup = companyGroupConverter
                .update(companyGroupFoundById, companyGroupUpdateRequest);

        return companyGroupConverter.convert(companyGroupRepository.save(companyGroup));
    }

    public String deleteCompany(Long adminId, Long companyFleetGroupId,
                                Long companyDistrictGroupId, Long companyGroupId) {

        Company companyFoundByAdminId = companyService.findCompanyByAdminId(adminId);
        CompanyFleetGroup companyFleetGroupFoundById = companyFleetGroupService
                .getCompanyFleetGroupById(companyFleetGroupId);
        CompanyDistrictGroup companyDistrictGroupFoundById = companyGroupDistrictService
                .getCompanyDistrictGroupById(companyDistrictGroupId);

        CompanyGroup companyGroupFoundById = getCompanyGroupById(companyGroupId);

        checkCompanyGroupAndAdminMatch(companyGroupFoundById, companyFoundByAdminId);
        checkCompanyDistrictGroupAndGroupMatch(companyDistrictGroupFoundById, companyGroupFoundById);
        checkCompanyDistrictGroupAndAdminMatch(companyDistrictGroupFoundById, companyFoundByAdminId);
        checkCompanyFleetGroupAndDistrictGroupMatch(companyDistrictGroupFoundById, companyFleetGroupFoundById);
        checkCompanyFleetGroupAndAdminMatch(companyFleetGroupFoundById, companyFoundByAdminId);

        companyGroupRepository.delete(companyGroupFoundById);

        return Constants.COMPANY_GROUP_DELETED;
    }


    public CompanyGroup getCompanyGroupById(Long id) {
        return companyGroupRepository.findById(id).orElseThrow(()
                -> new CompanyGroupNotExistException(Messages.CompanyGroup.NOT_EXISTS + id));
    }


    public List<CompanyGroup> getCompanyDistrictGroupById(Long id) {
        return companyGroupRepository.findAllByCompanyDistrictGroupId(id);

    }

    private void checkNameAvailable(Company company, String name) {

        if (companyGroupRepository.findByNameAndCompanyId(company.getId(), name).isPresent()) {
            throw new CompanyFleetGroupNameInUseException(Messages.CompanyFleetGroup.NAME_IN_USE
                    + name);
        }
    }

}
