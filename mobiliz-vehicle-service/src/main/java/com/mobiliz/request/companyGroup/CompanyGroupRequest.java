package com.mobiliz.request.companyGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyGroupRequest {

    @NotEmpty(message = "Please enter company group name")
    private String name;
    @NotNull(message = "Please enter district group id")
    private Long companyDistrictGroupId;
    @NotNull(message = "Please enter company id")
    private Long companyId;
    @NotNull(message = "Please enter company fleet group id")
    private Long companyFleetGroupId;
    public CompanyGroupRequest() {
    }

    public CompanyGroupRequest(String name, Long companyDistrictGroupId, Long companyId, Long companyFleetGroupId) {
        this.name = name;
        this.companyDistrictGroupId = companyDistrictGroupId;
        this.companyId = companyId;
        this.companyFleetGroupId = companyFleetGroupId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyFleetGroupId() {
        return companyFleetGroupId;
    }

    public void setCompanyFleetGroupId(Long companyFleetGroupId) {
        this.companyFleetGroupId = companyFleetGroupId;
    }

    @Override
    public String toString() {
        return "CompanyGroupRequest{" +
                "name='" + name + '\'' +
                ", companyDistrictGroupId=" + companyDistrictGroupId +
                ", companyId=" + companyId +
                '}';
    }
}
