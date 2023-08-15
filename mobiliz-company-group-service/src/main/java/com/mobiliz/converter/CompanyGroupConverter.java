package com.mobiliz.converter;

import com.mobiliz.model.CompanyGroup;
import com.mobiliz.request.CompanyGroupRequest;
import com.mobiliz.request.CompanyGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictCompanyGroupResponse;
import com.mobiliz.response.CompanyGroupResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyGroupConverter {

    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyGroup convert(CompanyGroupRequest companyGroupRequest){
        CompanyGroup companyGroup = new CompanyGroup();
        companyGroup.setName(companyGroupRequest.getName());
        return companyGroup;
    }

    public CompanyGroupResponse convert(CompanyGroup companyGroup){
        CompanyGroupResponse companyGroupResponse = new CompanyGroupResponse();
        companyGroupResponse.setId(companyGroup.getId());
        companyGroupResponse.setName(companyGroup.getName());
        companyGroupResponse.setCompanyId(companyGroup.getCompanyId());
        companyGroupResponse.setCompanyName(companyGroup.getCompanyName());
        companyGroupResponse.setCompanyFleetGroupId(companyGroup.getCompanyFleetId());
        companyGroupResponse.setCompanyFleetGroupName(companyGroup.getCompanyFleetName());
        companyGroupResponse.setCompanyDistrictGroupId(companyGroup.getCompanyDistrictGroupId());
        companyGroupResponse.setCompanyDistrictGroupName(companyGroup.getCompanyDistrictGroupName());
        return  companyGroupResponse;
    }

    public List<CompanyGroupResponse> convert(List<CompanyGroup> companyGroupList) {
        logger.info("convert companyGroupList to CompanyGroupResponse method started");
        List<CompanyGroupResponse> companyGroupResponses = new ArrayList<>();
        companyGroupList.forEach(companyGroup -> companyGroupResponses.add(convert(companyGroup)));
        logger.info("convert companyGroupList to companyGroupList method successfully worked");
        return companyGroupResponses;
    }

    public CompanyGroup update(CompanyGroup companyGroup, CompanyGroupUpdateRequest companyGroupUpdateRequest) {
        companyGroup.setName(companyGroupUpdateRequest.getName());
        return companyGroup;
    }


    public CompanyDistrictCompanyGroupResponse convertDistrictCompanyGroupResponse(CompanyGroup companyGroup){
        CompanyDistrictCompanyGroupResponse response = new CompanyDistrictCompanyGroupResponse();
        response.setId(companyGroup.getId());
        response.setName(companyGroup.getName());
        return  response;
    }

    public List<CompanyDistrictCompanyGroupResponse> convertDistrictCompanyGroupResponses(List<CompanyGroup> companyGroups) {
        logger.info("convert companyGroups to CompanyDistrictCompanyGroupResponse method started");
        List<CompanyDistrictCompanyGroupResponse> companyGroupResponses = new ArrayList<>();
        companyGroups.forEach(companyGroup -> companyGroupResponses.add(convertDistrictCompanyGroupResponse(companyGroup)));
        logger.info("convert companyGroups to CompanyDistrictCompanyGroupResponse method successfully worked");
        return companyGroupResponses;

    }
}
