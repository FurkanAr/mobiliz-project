package com.mobiliz.response;


import java.util.List;

public class CompanyResponse {

    private Long id;
    private String name;
    private List<CompanyFleetGroupResponse> companyFleetGroupResponses;

    public CompanyResponse() {
    }

    public CompanyResponse(Long id, String name, List<CompanyFleetGroupResponse> companyFleetGroupResponses) {
        this.id = id;
        this.name = name;
        this.companyFleetGroupResponses = companyFleetGroupResponses;
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

    public List<CompanyFleetGroupResponse> getCompanyFleetGroupResponses() {
        return companyFleetGroupResponses;
    }

    public void setCompanyFleetGroupResponses(List<CompanyFleetGroupResponse> companyFleetGroupResponses) {
        this.companyFleetGroupResponses = companyFleetGroupResponses;
    }

    @Override
    public String toString() {
        return "CompanyResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
