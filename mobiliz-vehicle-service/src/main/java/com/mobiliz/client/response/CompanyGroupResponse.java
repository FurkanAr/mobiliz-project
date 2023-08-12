package com.mobiliz.client.response;

public class CompanyGroupResponse {

    private Long id;
    private String name;
    private Long companyId;
    private String companyName;
    private Long companyFleetGroupId;
    private String companyFleetGroupName;
    private Long companyDistrictGroupId;
    private String companyDistrictGroupName;

    public CompanyGroupResponse() {
    }

    public CompanyGroupResponse(Long id, String name, Long companyId, String companyName, Long companyFleetGroupId,
                                String companyFleetGroupName, Long companyDistrictGroupId, String companyDistrictGroupName) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyFleetGroupId = companyFleetGroupId;
        this.companyFleetGroupName = companyFleetGroupName;
        this.companyDistrictGroupId = companyDistrictGroupId;
        this.companyDistrictGroupName = companyDistrictGroupName;
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

    public Long getCompanyDistrictGroupId() {
        return companyDistrictGroupId;
    }

    public void setCompanyDistrictGroupId(Long companyDistrictGroupId) {
        this.companyDistrictGroupId = companyDistrictGroupId;
    }

    public String getCompanyDistrictGroupName() {
        return companyDistrictGroupName;
    }

    public void setCompanyDistrictGroupName(String companyDistrictGroupName) {
        this.companyDistrictGroupName = companyDistrictGroupName;
    }

    @Override
    public String toString() {
        return "CompanyGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", companyFleetGroupId=" + companyFleetGroupId +
                ", companyDistrictGroupId=" + companyDistrictGroupId +
                '}';
    }
}
