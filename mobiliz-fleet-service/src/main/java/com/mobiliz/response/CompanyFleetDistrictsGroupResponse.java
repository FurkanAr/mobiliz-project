package com.mobiliz.response;

import com.mobiliz.client.response.CompanyFleetDistrictGroupResponse;

import java.util.List;

public class CompanyFleetDistrictsGroupResponse {

    private Long id;
    private String name;
    private Long companyId;
    private String companyName;
    private List<CompanyFleetDistrictGroupResponse> companyDistrictGroups;

    public CompanyFleetDistrictsGroupResponse() {
    }

    public CompanyFleetDistrictsGroupResponse(Long id, String name, Long companyId, String companyName,
                                              List<CompanyFleetDistrictGroupResponse> companyDistrictGroups) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyDistrictGroups = companyDistrictGroups;
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

    public List<CompanyFleetDistrictGroupResponse> getCompanyDistrictGroups() {
        return companyDistrictGroups;
    }

    public void setCompanyDistrictGroups(List<CompanyFleetDistrictGroupResponse> companyDistrictGroups) {
        this.companyDistrictGroups = companyDistrictGroups;
    }

    @Override
    public String toString() {
        return "CompanyFleetDistrictsGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
