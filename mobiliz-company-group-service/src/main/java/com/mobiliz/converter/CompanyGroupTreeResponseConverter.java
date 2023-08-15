package com.mobiliz.converter;

import com.mobiliz.model.CompanyGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyGroupTreeResponseConverter {

    Logger logger = LoggerFactory.getLogger(getClass());

    public CompanyGroupTreeResponse convert(CompanyGroup companyGroup){
        CompanyGroupTreeResponse response = new CompanyGroupTreeResponse();
        response.setId(companyGroup.getId());
        response.setCompanyGroupName(companyGroup.getName());
        return  response;
    }

    public List<CompanyGroupTreeResponse> convert(List<CompanyGroup> companyGroupList) {
        logger.info("convert companyGroupList to CompanyGroupResponse method started");
        List<CompanyGroupTreeResponse> companyGroupResponses = new ArrayList<>();
        companyGroupList.forEach(companyGroup -> companyGroupResponses.add(convert(companyGroup)));
        logger.info("convert companyGroupList to companyGroupList method successfully worked");
        return companyGroupResponses;
    }
}
