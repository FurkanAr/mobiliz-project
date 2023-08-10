package com.mobiliz.response.companyFleetGroup;


import java.util.List;

public class CompanyFleetGroupResponse {

    private Long id;
    private String name;

    public CompanyFleetGroupResponse() {
    }

    public CompanyFleetGroupResponse(Long id, String name, Long companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
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

    @Override
    public String toString() {
        return "CompanyFleetGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
