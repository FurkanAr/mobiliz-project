package com.mobiliz.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyFleetGroupRequest {

    @NotEmpty(message = "Please enter fleet name")
    private String name;
    @NotNull(message = "Please enter company id")
    private Long companyId;

    public CompanyFleetGroupRequest() {
    }

    public CompanyFleetGroupRequest(String name, Long companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CompanyFleetGroupRequest{" +
                "name='" + name + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
