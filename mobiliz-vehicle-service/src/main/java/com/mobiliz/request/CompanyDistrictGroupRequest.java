package com.mobiliz.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyDistrictGroupRequest {

    @NotEmpty(message = "Please enter group name")
    private String name;
    @NotNull(message = "Please enter group id")
    private Long companyFleetGroupId;

    public CompanyDistrictGroupRequest() {
    }

    public CompanyDistrictGroupRequest(String name, Long companyFleetGroupId) {
        this.name = name;
        this.companyFleetGroupId = companyFleetGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyFleetGroupId() {
        return companyFleetGroupId;
    }

    public void setCompanyFleetGroupId(Long companyFleetGroupId) {
        this.companyFleetGroupId = companyFleetGroupId;
    }

    @Override
    public String toString() {
        return "CompanyDistrictGroupRequest{" +
                "name='" + name + '\'' +
                ", companyFleetGroupId=" + companyFleetGroupId +
                '}';
    }
}
