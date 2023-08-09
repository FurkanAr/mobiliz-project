package com.mobiliz.response;


import java.util.List;

public class CompanyFleetGroupResponse {

    private Long id;
    private String name;
    private List<CompanyDistrictGroupResponse> companyDistrictGroupResponses;

    public CompanyFleetGroupResponse() {
    }

    public CompanyFleetGroupResponse(Long id, String name, List<CompanyDistrictGroupResponse> companyDistrictGroupResponses) {
        this.id = id;
        this.name = name;
        this.companyDistrictGroupResponses = companyDistrictGroupResponses;
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

    public List<CompanyDistrictGroupResponse> getCompanyDistrictGroupResponses() {
        return companyDistrictGroupResponses;
    }

    public void setCompanyDistrictGroupResponses(List<CompanyDistrictGroupResponse> companyDistrictGroupResponses) {
        this.companyDistrictGroupResponses = companyDistrictGroupResponses;
    }

    @Override
    public String toString() {
        return "CompanyFleetGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
