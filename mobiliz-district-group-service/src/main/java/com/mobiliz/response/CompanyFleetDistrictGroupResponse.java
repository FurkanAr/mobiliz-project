package com.mobiliz.response;

import java.util.List;

public class CompanyFleetDistrictGroupResponse {

    private Long id;
    private String name;

        private List<CompanyDistrictCompanyGroupResponse> companyGroups;
    public CompanyFleetDistrictGroupResponse() {
    }

    public CompanyFleetDistrictGroupResponse(Long id, String name, List<CompanyDistrictCompanyGroupResponse> companyGroups) {
        this.id = id;
        this.name = name;
        this.companyGroups = companyGroups;
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

    public List<CompanyDistrictCompanyGroupResponse> getCompanyGroups() {
        return companyGroups;
    }

    public void setCompanyGroups(List<CompanyDistrictCompanyGroupResponse> companyGroups) {
        this.companyGroups = companyGroups;
    }

    @Override
    public String toString() {
        return "CompanyFleetDistrictGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
