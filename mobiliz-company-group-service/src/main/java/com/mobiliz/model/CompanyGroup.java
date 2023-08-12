package com.mobiliz.model;

import javax.persistence.*;

@Entity
@Table(name = "company_group")
public class CompanyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "company_id")
    private Long companyId;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "company_fleet_id")
    private Long companyFleetId;
    @Column(name = "company_fleet_name")
    private String companyFleetName;
    @Column(name = "company_district_group_id")
    private Long companyDistrictGroupId;
    @Column(name = "company_district_group_name")
    private String companyDistrictGroupName;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "sur_name")
    private String surName;

    public CompanyGroup() {
    }

    public CompanyGroup(String name, Long companyId, String companyName, Long companyFleetId,
                        String companyFleetName, Long companyDistrictGroupId, String companyDistrictGroupName) {
        this.name = name;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyFleetId = companyFleetId;
        this.companyFleetName = companyFleetName;
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

    public Long getCompanyFleetId() {
        return companyFleetId;
    }

    public void setCompanyFleetId(Long companyFleetId) {
        this.companyFleetId = companyFleetId;
    }

    public String getCompanyFleetName() {
        return companyFleetName;
    }

    public void setCompanyFleetName(String companyFleetName) {
        this.companyFleetName = companyFleetName;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    @Override
    public String toString() {
        return "CompanyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", companyFleetId=" + companyFleetId +
                ", companyFleetName='" + companyFleetName + '\'' +
                ", companyDistrictGroupId=" + companyDistrictGroupId +
                ", companyDistrictGroupName=" + companyDistrictGroupName +
                '}';
    }
}
