package com.mobiliz.response;

public class CompanyDistrictCompanyGroupResponse {

    private Long id;
    private String name;

    public CompanyDistrictCompanyGroupResponse() {
    }

    public CompanyDistrictCompanyGroupResponse(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "CompanyDistrictCompanyGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
