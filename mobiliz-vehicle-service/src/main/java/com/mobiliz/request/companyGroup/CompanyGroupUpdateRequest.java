package com.mobiliz.request.companyGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyGroupUpdateRequest {

    @NotEmpty(message = "Please enter company group name")
    private String name;
    @NotNull(message = "Please enter district group id")
    private Long companyDistrictGroupId;

    public CompanyGroupUpdateRequest() {
    }

    public CompanyGroupUpdateRequest(String name, Long companyDistrictGroupId) {
        this.name = name;
        this.companyDistrictGroupId = companyDistrictGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyDistrictGroupId() {
        return companyDistrictGroupId;
    }

    public void setCompanyDistrictGroupId(Long companyDistrictGroupId) {
        this.companyDistrictGroupId = companyDistrictGroupId;
    }

    @Override
    public String toString() {
        return "CompanyGroupUpdateRequest{" +
                "name='" + name + '\'' +
                ", companyDistrictGroupId=" + companyDistrictGroupId +
                '}';
    }
}
