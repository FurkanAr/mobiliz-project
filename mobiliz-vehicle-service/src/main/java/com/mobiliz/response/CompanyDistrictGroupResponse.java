package com.mobiliz.response;

import com.mobiliz.model.CompanyGroup;
import com.mobiliz.model.Vehicle;

import java.util.List;

public class CompanyDistrictGroupResponse {

    private Long id;
    private String name;
    private Long companyId;
    private String companyName;
    private Long companyFleetGroupId;
    private String companyFleetGroupName;


    public CompanyDistrictGroupResponse() {
    }

    public CompanyDistrictGroupResponse(Long id, String name, Long companyId, String companyName, Long companyFleetGroupId, String companyFleetGroupName) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyFleetGroupId = companyFleetGroupId;
        this.companyFleetGroupName = companyFleetGroupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCompanyFleetGroupId() {
        return companyFleetGroupId;
    }

    public void setCompanyFleetGroupId(Long companyFleetGroupId) {
        this.companyFleetGroupId = companyFleetGroupId;
    }

    public String getCompanyFleetGroupName() {
        return companyFleetGroupName;
    }

    public void setCompanyFleetGroupName(String companyFleetGroupName) {
        this.companyFleetGroupName = companyFleetGroupName;
    }

    @Override
    public String toString() {
        return "CompanyDistrictGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", companyFleetGroupId=" + companyFleetGroupId +
                '}';
    }
}
