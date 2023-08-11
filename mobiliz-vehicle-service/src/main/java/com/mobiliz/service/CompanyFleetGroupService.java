package com.mobiliz.service;

import com.mobiliz.constant.Constants;
import com.mobiliz.converter.CompanyFleetGroupConverter;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNameInUseException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.Company;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.repository.CompanyFleetGroupRepository;
import com.mobiliz.request.companyFleetGroup.CompanyFleetGroupRequest;
import com.mobiliz.request.companyFleetGroup.CompanyFleetUpdateRequest;
import com.mobiliz.response.CompanyFleetGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mobiliz.utils.CompanyMatch.checkAdminIdAndCompanyAdminIdMatch;
import static com.mobiliz.utils.CompanyMatch.checkCompanyFleetGroupAndAdminMatch;

@Service
public class CompanyFleetGroupService {

    private final CompanyFleetGroupRepository companyFleetGroupRepository;
    private final CompanyService companyService;
    private final CompanyFleetGroupConverter companyFleetGroupConverter;

    public CompanyFleetGroupService(CompanyFleetGroupRepository companyFleetGroupRepository, CompanyService companyService, CompanyFleetGroupConverter companyFleetGroupConverter) {
        this.companyFleetGroupRepository = companyFleetGroupRepository;
        this.companyService = companyService;
        this.companyFleetGroupConverter = companyFleetGroupConverter;
    }

    public List<CompanyFleetGroupResponse> getCompanyFleetGroups(Long adminId) {
        Company company = companyService.findCompanyByAdminId(adminId);

        List<CompanyFleetGroup> companyFleetGroups = companyFleetGroupRepository.findAllByCompanyId(company.getId())
                .orElseThrow(() -> new CompanyFleetGroupNotFoundException(Messages.CompanyFleetGroup.NOT_EXISTS_BY_GIVEN_COMPANY_ID + company.getId()));

        return companyFleetGroupConverter.convert(companyFleetGroups);

    }

    @Transactional
    public CompanyFleetGroupResponse createCompanyFleetGroup(Long adminId, CompanyFleetGroupRequest companyFleetGroupRequest) {
        Company company = companyService.getCompanyById(companyFleetGroupRequest.getCompanyId());

        checkAdminIdAndCompanyAdminIdMatch(adminId, company);

        checkNameAvailable(company, companyFleetGroupRequest.getName());

        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter.convert(companyFleetGroupRequest);
        companyFleetGroup = companyFleetGroupRepository.save(companyFleetGroup);
        companyFleetGroup.setCompany(company);
        return companyFleetGroupConverter.convert(companyFleetGroupRepository.save(companyFleetGroup));
    }

    @Transactional
    public CompanyFleetGroupResponse updateCompanyFleetGroup(Long adminId, Long companyFleetGroupId,
                                                             CompanyFleetUpdateRequest companyFleetUpdateRequest) {
        Company companyFoundByAdminId = companyService.findCompanyByAdminId(adminId);
        CompanyFleetGroup companyFleetGroupFoundById = getCompanyFleetGroupById(companyFleetGroupId);

        checkCompanyFleetGroupAndAdminMatch(companyFleetGroupFoundById, companyFoundByAdminId);

        checkNameAvailable(companyFoundByAdminId, companyFleetUpdateRequest.getName());

        CompanyFleetGroup companyFleetGroup = companyFleetGroupConverter
                .update(companyFleetGroupFoundById, companyFleetUpdateRequest);

        return companyFleetGroupConverter.convert(companyFleetGroupRepository.save(companyFleetGroup));
    }

    @Transactional
    public String deleteCompanyFleetGroup(Long adminId, Long companyFleetGroupId) {
        Company companyFoundByAdminId = companyService.findCompanyByAdminId(adminId);
        CompanyFleetGroup companyFleetGroupFoundById = getCompanyFleetGroupById(companyFleetGroupId);

        checkCompanyFleetGroupAndAdminMatch(companyFleetGroupFoundById, companyFoundByAdminId);

        companyFleetGroupRepository.delete(companyFleetGroupFoundById);
        return Constants.COMPANY_FLEET_GROUP_DELETED;
    }

    public CompanyFleetGroup getCompanyFleetGroupById(Long id) {
        return companyFleetGroupRepository.findById(id).orElseThrow(()
                -> new CompanyFleetGroupNotFoundException(Messages.CompanyFleetGroup.NOT_EXISTS + id));
    }

    private void checkNameAvailable(Company company, String name) {

        if (companyFleetGroupRepository.findByNameAndCompanyId(company.getId(), name).isPresent()) {
            throw new CompanyFleetGroupNameInUseException(Messages.CompanyFleetGroup.NAME_IN_USE
                    + name);
        }

    }



}
