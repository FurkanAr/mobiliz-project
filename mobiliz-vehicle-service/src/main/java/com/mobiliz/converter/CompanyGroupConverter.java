package com.mobiliz.converter;

import com.mobiliz.model.CompanyGroup;
import com.mobiliz.request.companyGroup.CompanyGroupRequest;
import com.mobiliz.request.companyGroup.CompanyGroupUpdateRequest;
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
}
