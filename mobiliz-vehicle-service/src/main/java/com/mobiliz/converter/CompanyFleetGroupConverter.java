package com.mobiliz.converter;

import com.mobiliz.model.CompanyFleetGroup;
import com.mobiliz.request.companyFleetGroup.CompanyFleetGroupRequest;
import com.mobiliz.request.companyFleetGroup.CompanyFleetUpdateRequest;
import com.mobiliz.response.companyFleetGroup.CompanyFleetGroupResponse;
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
        return companyFleetGroup;
    }

    public CompanyFleetGroupResponse convert(CompanyFleetGroup companyFleetGroup){
        CompanyFleetGroupResponse response = new CompanyFleetGroupResponse();
        response.setId(companyFleetGroup.getId());
        response.setName(companyFleetGroup.getName());
        response.setCompanyId(companyFleetGroup.getCompany().getId());
        return response;
    }

    public List<CompanyFleetGroupResponse> convert(List<CompanyFleetGroup> companyFleetGroups) {
        logger.info("convert companyFleetGroups to CompanyFleetGroupResponse method started");
        List<CompanyFleetGroupResponse> companyFleetGroupResponses = new ArrayList<>();
        companyFleetGroups.forEach(companyGroup -> companyFleetGroupResponses.add(convert(companyGroup)));
        logger.info("convert companyFleetGroups to CompanyFleetGroupResponse method successfully worked");
        return companyFleetGroupResponses;
    }

    public CompanyFleetGroup update(CompanyFleetGroup company,
                                    CompanyFleetUpdateRequest companyFleetGroupRequest) {
        company.setName(companyFleetGroupRequest.getName());
        return company;
    }
}
