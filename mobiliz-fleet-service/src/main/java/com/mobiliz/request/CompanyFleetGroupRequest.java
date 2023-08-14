package com.mobiliz.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyFleetGroupRequest {

    @NotEmpty(message = "Please enter fleet name")
    private String name;
    @NotNull(message = "Please enter company id")
    private Long companyId;
    @NotEmpty(message = "Please enter company name")
    private String companyName;
    @NotNull(message = "Please enter admin id")
    private Long adminId;
    @NotEmpty(message = "Please enter admin first name")
    private String firstName;
    @NotEmpty(message = "Please enter admin surname")
    private String surName;


    public CompanyFleetGroupRequest() {
    }

    public CompanyFleetGroupRequest(String name, Long companyId, String companyName, Long adminId, String firstName, String surName) {
        this.name = name;
        this.companyId = companyId;
        this.companyName = companyName;
        this.adminId = adminId;
        this.firstName = firstName;
        this.surName = surName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    @Override
    public String toString() {
        return "CompanyFleetGroupRequest{" +
                "name='" + name + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
