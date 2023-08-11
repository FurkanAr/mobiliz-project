package com.mobiliz.service;

import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyDistrictGroupConverter;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupNotFoundException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNameInUseException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.Company;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.repository.CompanyDistrictGroupRepository;
import com.mobiliz.request.companyDistrictGroup.CompanyDistrictGroupRequest;
import com.mobiliz.request.companyDistrictGroup.CompanyDistrictGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mobiliz.utils.CompanyMatch.*;

@Service
public class CompanyDistrictGroupService {

    private final CompanyDistrictGroupRepository companyDistrictGroupRepository;
    private final CompanyFleetGroupService companyFleetGroupService;
    private final CompanyDistrictGroupConverter companyDistrictGroupConverter;
    private final CompanyService companyService;

    public CompanyDistrictGroupService(CompanyDistrictGroupRepository companyDistrictGroupRepository, CompanyFleetGroupService companyFleetGroupService, CompanyDistrictGroupConverter companyDistrictGroupConverter, CompanyService companyService) {
        this.companyDistrictGroupRepository = companyDistrictGroupRepository;
        this.companyFleetGroupService = companyFleetGroupService;
        this.companyDistrictGroupConverter = companyDistrictGroupConverter;
        this.companyService = companyService;
    }

    public List<CompanyDistrictGroupResponse> getCompanyDistrictGroups(Long adminId) {
        Company company = companyService.findCompanyByAdminId(adminId);

        List<CompanyDistrictGroup> companyDistrictGroups = companyDistrictGroupRepository.findAllByCompanyId(company.getId())
                .orElseThrow(() -> new CompanyDistrictGroupNotFoundException(Messages.CompanyDistrictGroup.NOT_EXISTS_BY_GIVEN_COMPANY_ID + company.getId()));

        return companyDistrictGroupConverter.convert(companyDistrictGroups);
    }

    @Transactional
    public CompanyDistrictGroupResponse createCompanyDistrictGroup(Long adminId, CompanyDistrictGroupRequest companyDistrictGroupRequest) {
        Company company = companyService.getCompanyById(companyDistrictGroupRequest.getCompanyId());

        checkAdminIdAndCompanyAdminIdMatch(adminId, company);

        CompanyFleetGroup companyFleetGroup = companyFleetGroupService
                .getCompanyFleetGroupById(companyDistrictGroupRequest.getCompanyFleetGroupId());

        checkCompanyFleetGroupAndAdminMatch(companyFleetGroup, company);

        checkNameAvailable(company, companyDistrictGroupRequest.getName());

        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupConverter.convert(companyDistrictGroupRequest);
        companyDistrictGroup = companyDistrictGroupRepository.save(companyDistrictGroup);
        companyDistrictGroup.setCompanyFleetGroup(companyFleetGroup);
        companyDistrictGroup.setCompany(company);
        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }


    @Transactional
    public CompanyDistrictGroupResponse updateCompanyDistrictGroup(Long adminId, Long companyFleetGroupId,
                                                                   Long companyDistrictGroupId,
                                                                   CompanyDistrictGroupUpdateRequest companyDistrictGroupUpdateRequest) {
        Company companyFoundByAdminId = companyService.findCompanyByAdminId(adminId);
        CompanyFleetGroup companyFleetGroupFoundById = companyFleetGroupService
                .getCompanyFleetGroupById(companyFleetGroupId);
        CompanyDistrictGroup companyDistrictGroupFoundById = getCompanyDistrictGroupById(companyDistrictGroupId);

        checkCompanyDistrictGroupAndAdminMatch(companyDistrictGroupFoundById, companyFoundByAdminId);
        checkCompanyFleetGroupAndDistrictGroupMatch(companyDistrictGroupFoundById, companyFleetGroupFoundById);
        checkCompanyFleetGroupAndAdminMatch(companyFleetGroupFoundById, companyFoundByAdminId);

        checkNameAvailable(companyFoundByAdminId, companyDistrictGroupUpdateRequest.getName());

        CompanyDistrictGroup companyDistrictGroup = companyDistrictGroupConverter
                .update(companyDistrictGroupFoundById, companyDistrictGroupUpdateRequest);

        return companyDistrictGroupConverter.convert(companyDistrictGroupRepository.save(companyDistrictGroup));
    }

    @Transactional
    public String deleteCompanyFleetGroup(Long adminId, Long companyFleetGroupId, Long companyDistrictGroupId) {
        Company companyFoundByAdminId = companyService.findCompanyByAdminId(adminId);
        System.out.println("companyFoundByAdminId: " + companyFoundByAdminId);
        CompanyFleetGroup companyFleetGroupFoundById = companyFleetGroupService
                .getCompanyFleetGroupById(companyFleetGroupId);
        System.out.println("companyFleetGroupFoundById: " + companyFleetGroupFoundById);
        CompanyDistrictGroup companyDistrictGroupFoundById = getCompanyDistrictGroupById(companyDistrictGroupId);

        System.out.println("companyDistrictGroupFoundById: " + companyDistrictGroupFoundById);

        checkCompanyDistrictGroupAndAdminMatch(companyDistrictGroupFoundById, companyFoundByAdminId);
        checkCompanyFleetGroupAndDistrictGroupMatch(companyDistrictGroupFoundById, companyFleetGroupFoundById);
        checkCompanyFleetGroupAndAdminMatch(companyFleetGroupFoundById, companyFoundByAdminId);


        companyDistrictGroupRepository.delete(companyDistrictGroupFoundById);

        return Constants.COMPANY_DISTRICT_GROUP_DELETED;
    }

    public CompanyDistrictGroup getCompanyDistrictGroupById(Long id) {
        return companyDistrictGroupRepository.findById(id).orElseThrow(()
                -> new CompanyDistrictGroupNotFoundException(Messages.CompanyDistrictGroup.NOT_EXISTS + id));
    }

    public List<CompanyDistrictGroup> getCompanyFleetGroupById(Long id) {
        return companyDistrictGroupRepository.findAllByCompanyFleetGroupId(id);
    }

    private void checkNameAvailable(Company company, String name) {

        if (companyDistrictGroupRepository.findByNameAndCompanyId(company.getId(), name).isPresent()) {
            throw new CompanyFleetGroupNameInUseException(Messages.CompanyFleetGroup.NAME_IN_USE
                    + name);
        }
    }


}
