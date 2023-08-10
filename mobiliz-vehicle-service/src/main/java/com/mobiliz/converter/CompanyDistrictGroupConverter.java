package com.mobiliz.converter;

import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.request.companyDistrictGroup.CompanyDistrictGroupRequest;
import com.mobiliz.request.companyDistrictGroup.CompanyDistrictGroupUpdateRequest;
import com.mobiliz.response.CompanyDistrictGroupResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyDistrictGroupConverter {


    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyDistrictGroup convert(CompanyDistrictGroupRequest companyDistrictGroupRequest){
        CompanyDistrictGroup companyDistrictGroup = new CompanyDistrictGroup();
        companyDistrictGroup.setName(companyDistrictGroupRequest.getName());
        return companyDistrictGroup;
    }

    public CompanyDistrictGroupResponse convert(CompanyDistrictGroup companyDistrictGroup){
        CompanyDistrictGroupResponse response = new CompanyDistrictGroupResponse();
        response.setId(companyDistrictGroup.getId());
        response.setName(companyDistrictGroup.getName());
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
        companyDistrictGroup.setName(companyDistrictGroupUpdateRequest.getName());
        return companyDistrictGroup;
    }
}
