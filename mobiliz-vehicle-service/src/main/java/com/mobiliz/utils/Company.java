package com.mobiliz.utils;

import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyFleetGroup.CompanyIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyFleetGroup;

public class Company {

    public static void checkCompanyFleetGroupAndAdminMatch(CompanyFleetGroup companyFleetGroupFoundById, com.mobiliz.model.Company companyFoundByAdminId) {
        if(!companyFoundByAdminId.getId().equals(companyFleetGroupFoundById.getCompany().getId())){
            throw new CompanyFleetGroupIdAndAdminIdNotMatchedException(
                    Messages.CompanyFleetGroup.COMPANY_FLEET_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED + companyFoundByAdminId);
        }
    }

    public static void checkAdminIdAndCompanyAdminIdMatch(Long adminId, com.mobiliz.model.Company company) {
        if (!adminId.equals(company.getAdminId())) {
            throw new CompanyIdAndAdminIdNotMatchedException(Messages.Company.ADMIN_NOT_MATCHED + adminId);
        }
    }
}
