package com.mobiliz.converter;

import com.mobiliz.client.response.CompanyFleetDistrictGroupResponse;
import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.request.CompanyFleetGroupRequest;
import com.mobiliz.response.CompanyFleetDistrictsGroupResponse;
import com.mobiliz.response.CompanyFleetGroupResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyFleetGroupConverter {

    Logger logger = LoggerFactory.getLogger(getClass());


    public CompanyFleetGroup convert(CompanyFleetGroupRequest companyFleetGroupRequest){
        CompanyFleetGroup companyFleetGroup = new CompanyFleetGroup();
        companyFleetGroup.setName(companyFleetGroupRequest.getName());
        companyFleetGroup.setCompanyId(companyFleetGroupRequest.getCompanyId());
        companyFleetGroup.setCompanyName(companyFleetGroupRequest.getCompanyName());
        companyFleetGroup.setAdminId(companyFleetGroupRequest.getAdminId());
        companyFleetGroup.setFirstName(companyFleetGroupRequest.getFirstName());
        companyFleetGroup.setSurName(companyFleetGroupRequest.getSurName());
        return companyFleetGroup;
    }

    public CompanyFleetGroupResponse convert(CompanyFleetGroup companyFleetGroup){
        CompanyFleetGroupResponse response = new CompanyFleetGroupResponse();
        response.setId(companyFleetGroup.getId());
        response.setName(companyFleetGroup.getName());
        response.setCompanyId(companyFleetGroup.getCompanyId());
        response.setCompanyName(companyFleetGroup.getCompanyName());
        return response;
    }

    public List<CompanyFleetGroupResponse> convert(List<CompanyFleetGroup> companyFleetGroups) {
        logger.info("convert companyFleetGroups to CompanyFleetGroupResponse method started");
        List<CompanyFleetGroupResponse> companyFleetGroupResponses = new ArrayList<>();
        companyFleetGroups.forEach(companyGroup -> companyFleetGroupResponses.add(convert(companyGroup)));
        logger.info("convert companyFleetGroups to CompanyFleetGroupResponse method successfully worked");
        return companyFleetGroupResponses;
    }


    public CompanyFleetDistrictsGroupResponse convertCompanyFleetDistrictsGroupResponse(
            CompanyFleetGroup companyFleetGroup, List<CompanyFleetDistrictGroupResponse> fleetDistrictGroupResponses) {

        CompanyFleetDistrictsGroupResponse response = new CompanyFleetDistrictsGroupResponse();
        response.setId(companyFleetGroup.getId());
        response.setName(companyFleetGroup.getName());
        response.setCompanyId(companyFleetGroup.getCompanyId());
        response.setCompanyName(companyFleetGroup.getCompanyName());
        response.setCompanyDistrictGroups(fleetDistrictGroupResponses);
        return response;
    }
}
