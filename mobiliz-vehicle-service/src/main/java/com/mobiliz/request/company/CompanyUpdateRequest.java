package com.mobiliz.request.company;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyUpdateRequest {

    @NotEmpty(message = "Please enter company name")
    private String name;
    @NotNull(message = "Please enter adminId")
    private Long adminId;
    @NotEmpty(message = "Please enter admin name")
    private String adminName;
    @NotEmpty(message = "Please enter admin surname")
    private String adminSurname;

    public CompanyUpdateRequest() {
    }

    public CompanyUpdateRequest(String name, Long adminId, String adminName, String adminSurname) {
        this.name = name;
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminSurname = adminSurname;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminSurname() {
        return adminSurname;
    }

    public void setAdminSurname(String adminSurname) {
        this.adminSurname = adminSurname;
    }

    @Override
    public String toString() {
        return "CompanyUpdateRequest{" +
                ", name='" + name + '\'' +
                ", adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", adminSurname='" + adminSurname + '\'' +
                '}';
    }
}
