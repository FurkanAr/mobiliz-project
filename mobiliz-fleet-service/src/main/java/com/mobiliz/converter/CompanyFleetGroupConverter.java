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
        logger.info("convert method started");
        CompanyFleetGroup companyFleetGroup = new CompanyFleetGroup();
        companyFleetGroup.setName(companyFleetGroupRequest.getName());
        companyFleetGroup.setCompanyId(companyFleetGroupRequest.getCompanyId());
        companyFleetGroup.setCompanyName(companyFleetGroupRequest.getCompanyName());
        companyFleetGroup.setAdminId(companyFleetGroupRequest.getAdminId());
        companyFleetGroup.setFirstName(companyFleetGroupRequest.getFirstName());
        companyFleetGroup.setSurName(companyFleetGroupRequest.getSurName());
        logger.info("convert method finished");
        return companyFleetGroup;
    }

    public CompanyFleetGroupResponse convert(CompanyFleetGroup companyFleetGroup){
        logger.info("convert method started");
        CompanyFleetGroupResponse response = new CompanyFleetGroupResponse();
        response.setId(companyFleetGroup.getId());
        response.setName(companyFleetGroup.getName());
        response.setCompanyId(companyFleetGroup.getCompanyId());
        response.setCompanyName(companyFleetGroup.getCompanyName());
        logger.info("convert method finished");
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
        logger.info("convertCompanyFleetDistrictsGroupResponse method started");
        CompanyFleetDistrictsGroupResponse response = new CompanyFleetDistrictsGroupResponse();
        response.setId(companyFleetGroup.getId());
        response.setName(companyFleetGroup.getName());
        response.setCompanyId(companyFleetGroup.getCompanyId());
        response.setCompanyName(companyFleetGroup.getCompanyName());
        response.setCompanyDistrictGroups(fleetDistrictGroupResponses);
        logger.info("convertCompanyFleetDistrictsGroupResponse method finished");
        return response;
    }
}
