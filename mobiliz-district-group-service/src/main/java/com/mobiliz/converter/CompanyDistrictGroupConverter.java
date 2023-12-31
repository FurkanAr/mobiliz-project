package com.mobiliz.converter;

import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.request.CompanyDistrictGroupRequest;
import com.mobiliz.request.CompanyDistrictGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import com.mobiliz.response.CompanyFleetDistrictGroupResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyDistrictGroupConverter {


    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyDistrictGroup convert(CompanyDistrictGroupRequest companyDistrictGroupRequest) {
        logger.info("convert method started");
        CompanyDistrictGroup companyDistrictGroup = new CompanyDistrictGroup();
        companyDistrictGroup.setName(companyDistrictGroupRequest.getName());
        logger.info("convert method finished");
        return companyDistrictGroup;
    }

    public CompanyDistrictGroupResponse convert(CompanyDistrictGroup companyDistrictGroup) {
        logger.info("convert method started");
        CompanyDistrictGroupResponse response = new CompanyDistrictGroupResponse();
        response.setId(companyDistrictGroup.getId());
        response.setName(companyDistrictGroup.getName());
        response.setCompanyId(companyDistrictGroup.getCompanyId());
        response.setCompanyName(companyDistrictGroup.getCompanyName());
        response.setCompanyFleetGroupId(companyDistrictGroup.getCompanyFleetId());
        response.setCompanyFleetGroupName(companyDistrictGroup.getCompanyFleetName());
        logger.info("convert method finished");
        return response;
    }

    public List<CompanyDistrictGroupResponse> convert(List<CompanyDistrictGroup> companyDistrictGroupList) {
        logger.info("convert companyDistrictGroupList to CompanyDistrictGroupResponse method started");
        List<CompanyDistrictGroupResponse> companyDistrictGroupResponses = new ArrayList<>();
        companyDistrictGroupList.forEach(companyGroup -> companyDistrictGroupResponses.add(convert(companyGroup)));
        logger.info("convert companyDistrictGroupList to CompanyDistrictGroupResponse method successfully worked");
        return companyDistrictGroupResponses;
    }


    public CompanyDistrictGroup update(CompanyDistrictGroup companyDistrictGroup, CompanyDistrictGroupUpdateRequest companyDistrictGroupUpdateRequest) {
        logger.info("update method started");
        companyDistrictGroup.setName(companyDistrictGroupUpdateRequest.getName());
        logger.info("update method finished");
        return companyDistrictGroup;
    }

    public CompanyFleetDistrictGroupResponse convertFleetResponse(
            CompanyDistrictGroup companyDistrictGroup) {
        logger.info("convertFleetResponse method started");

        CompanyFleetDistrictGroupResponse response = new CompanyFleetDistrictGroupResponse();
        response.setId(companyDistrictGroup.getId());
        response.setName(companyDistrictGroup.getName());
        logger.info("convertFleetResponse method finished");
        return response;
    }

    public List<CompanyFleetDistrictGroupResponse> convertFleetResponses(
            List<CompanyDistrictGroup> companyDistrictGroups) {

        logger.info("convert companyDistrictGroups to CompanyFleetDistrictGroupResponse method started");

        List<CompanyFleetDistrictGroupResponse> companyFleetDistrictGroupResponses = new ArrayList<>();

        companyDistrictGroups.forEach(companyGroup -> companyFleetDistrictGroupResponses
                .add(convertFleetResponse(companyGroup)));

        logger.info("convert companyDistrictGroups to CompanyFleetDistrictGroupResponse method successfully worked");
        return companyFleetDistrictGroupResponses;
    }
}
