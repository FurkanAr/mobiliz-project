package com.mobiliz.model;

import javax.persistence.*;

@Entity
@Table(name = "company_district_group")
public class CompanyDistrictGroup {

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
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "sur_name")
    private String surName;

    public CompanyDistrictGroup() {
    }

    public CompanyDistrictGroup(String name, Long companyId, String companyName, Long companyFleetId, String companyFleetName) {
        this.name = name;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyFleetId = companyFleetId;
        this.companyFleetName = companyFleetName;
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

    @Override
    public String toString() {
        return "CompanyDistrictGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", companyFleetId=" + companyFleetId +
                ", companyFleetName='" + companyFleetName + '\'' +
                '}';
    }
}
