package com.mobiliz.response;


import java.util.List;

public class CompanyFleetGroupResponse {

    private Long id;
    private String name;
    private Long companyId;
    private String companyName;

    public CompanyFleetGroupResponse() {
    }

    public CompanyFleetGroupResponse(Long id, String name, Long companyId, String companyName) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.companyName = companyName;
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

    @Override
    public String toString() {
        return "CompanyFleetGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
