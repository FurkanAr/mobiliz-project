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

        CompanyFleetGroup companyFleetGroup = companyFleetGroupService
                .getCompanyFleetGroupById(companyGroupRequest.getCompanyFleetGroupId());

        CompanyDistrictGroup companyDistrictGroup = companyGroupDistrictService
                .getCompanyDistrictGroupById(companyGroupRequest.getCompanyDistrictGroupId());


        checkAdminMatch(adminId, companyGroupRequest.getCompanyId());
        checkAdminMatch(adminId, companyFleetGroup.getCompany().getId());
        checkAdminMatch(adminId, companyDistrictGroup.getCompany().getId());

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

        CompanyGroup companyGroupFoundById = getCompanyGroupById(companyGroupId);

        Company companyFoundByAdminId = companyService.findCompanyByAdminId(adminId);

        checkAdminMatch(adminId, companyGroupFoundById.getCompany().getId());

        checkAdminMatch(adminId, companyGroupDistrictService.
                getCompanyDistrictGroupById(companyDistrictGroupId).getCompany().getId());

        checkAdminMatch(adminId, companyFleetGroupService
                .getCompanyFleetGroupById(companyFleetGroupId).getCompany().getId());

        checkNameAvailable(companyFoundByAdminId, companyGroupUpdateRequest.getName());

        CompanyGroup companyGroup = companyGroupConverter
                .update(companyGroupFoundById, companyGroupUpdateRequest);

        return companyGroupConverter.convert(companyGroup);
    }

    public String deleteCompany(Long adminId, Long companyFleetGroupId,
                                Long companyDistrictGroupId, Long companyGroupId) {

        CompanyGroup companyGroupFoundById = getCompanyGroupById(companyGroupId);

        checkAdminMatch(adminId, companyGroupFoundById.getCompany().getId());

        checkAdminMatch(adminId, companyGroupDistrictService.
                getCompanyDistrictGroupById(companyDistrictGroupId).getCompany().getId());

        checkAdminMatch(adminId, companyFleetGroupService
                .getCompanyFleetGroupById(companyFleetGroupId).getCompany().getId());

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

    private void checkAdminMatch(Long adminId, Long companyId) {
        Company companyFoundByAdminId = companyService.findCompanyByAdminId(adminId);
        Company companyFoundByCompanyId = companyService.getCompanyById(companyId);

        if (!companyFoundByCompanyId.getAdminId().equals(companyFoundByAdminId.getAdminId())) {
            throw new CompanyIdAndAdminIdNotMatchedException(Messages.Company.ADMIN_NOT_MATCHED + companyFoundByAdminId);
        }
    }
}
