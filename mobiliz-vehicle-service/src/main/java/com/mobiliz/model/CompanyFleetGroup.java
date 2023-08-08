package com.mobiliz.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company_fleet_group")
public class CompanyFleetGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "companyFleetGroup", cascade = CascadeType.ALL)
    private List<CompanyDistrictGroup> companyDistrictGroups = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "sur_name")
    private String surName;

    public CompanyFleetGroup() {
    }

    public CompanyFleetGroup(String name, Company company) {
        this.name = name;
        this.company = company;
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

    public List<CompanyDistrictGroup> getCompanyDistrictGroups() {
        return companyDistrictGroups;
    }

    public void setCompanyDistrictGroups(List<CompanyDistrictGroup> companyDistrictGroups) {
        this.companyDistrictGroups = companyDistrictGroups;
    }

    public void addCompanyDistrictGroup(CompanyDistrictGroup companyDistrictGroup){
        this.companyDistrictGroups.add(companyDistrictGroup);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        return "CompanyFleetGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company=" + company +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                '}';
    }
}
