package com.mobiliz.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyDistrictGroupRequest {

    @NotEmpty(message = "Please enter group name")
    private String name;
    @NotNull(message = "Please enter group id")
    private Long companyFleetGroupId;
    @NotNull(message = "Please enter company id")
    private Long companyId;

    public CompanyDistrictGroupRequest() {
    }

    public CompanyDistrictGroupRequest(String name, Long companyFleetGroupId, Long companyId) {
        this.name = name;
        this.companyFleetGroupId = companyFleetGroupId;
        this.companyId = companyId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CompanyDistrictGroupRequest{" +
                "name='" + name + '\'' +
                ", companyFleetGroupId=" + companyFleetGroupId +
                ", companyId=" + companyId +
                '}';
    }
}
