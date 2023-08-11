package com.mobiliz.utils;

import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupAndCompanyFleetGroupNotMatchException;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyFleetGroup.CompanyIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyGroup.CompanyGroupAndCompanyDistrictGroupNotMatchException;
import com.mobiliz.exception.companyGroup.CompanyGroupIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.model.CompanyGroup;

public class CompanyMatch {

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

    public static void checkCompanyDistrictGroupAndAdminMatch(CompanyDistrictGroup companyDistrictGroupFoundById, com.mobiliz.model.Company companyFoundByAdminId) {
        if(!companyFoundByAdminId.getId().equals(companyDistrictGroupFoundById.getCompany().getId())){
            throw new CompanyDistrictGroupIdAndAdminIdNotMatchedException(
                    Messages.CompanyDistrictGroup.COMPANY_DISTRICT_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED + companyFoundByAdminId);
        }
    }

    public static void checkCompanyGroupAndAdminMatch(CompanyGroup companyGroup, com.mobiliz.model.Company companyFoundByAdminId) {
        if(!companyFoundByAdminId.getId().equals(companyGroup.getCompany().getId())){
            throw new CompanyGroupIdAndAdminIdNotMatchedException(
                    Messages.CompanyGroup.COMPANY_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED + companyFoundByAdminId);
        }
    }

    public static void checkCompanyFleetGroupAndDistrictGroupMatch(CompanyDistrictGroup companyDistrictGroup, CompanyFleetGroup companyFleetGroup) {
            if(!companyDistrictGroup.getCompanyFleetGroup().getId().equals(companyFleetGroup.getId())){
                throw new CompanyDistrictGroupAndCompanyFleetGroupNotMatchException(
                        Messages.CompanyDistrictGroup.COMPANY_DISTRICT_GROUP_ID_AND_FLEET_GROUP_ID_NOT_MATCHED + companyFleetGroup.getId());
            }
        }

    public static void checkCompanyDistrictGroupAndGroupMatch(CompanyDistrictGroup companyDistrictGroup, CompanyGroup companyGroup) {
        if(!companyGroup.getCompanyDistrictGroup().getId().equals(companyDistrictGroup.getId())){
            throw new CompanyGroupAndCompanyDistrictGroupNotMatchException(
                    Messages.CompanyGroup.COMPANY_GROUP_ID_AND_DISTRICT_GROUP_ID_NOT_MATCHED + companyDistrictGroup.getId());
        }
    }

}
