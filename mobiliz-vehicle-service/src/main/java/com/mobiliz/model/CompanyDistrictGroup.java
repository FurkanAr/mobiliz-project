package com.mobiliz.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company_district_group")
public class CompanyDistrictGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "companyDistrictGroup", cascade = CascadeType.ALL)
    private List<CompanyGroup> companyGroups = new ArrayList<>();
    @OneToMany(mappedBy = "companyDistrictGroup", cascade = CascadeType.ALL)
    private List<Vehicle> vehicleList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "company_fleet_group_id")
    private CompanyFleetGroup companyFleetGroup;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "sur_name")
    private String surName;

    public CompanyDistrictGroup() {
    }

    public CompanyDistrictGroup(String name, CompanyFleetGroup companyFleetGroup) {
        this.name = name;
        this.companyFleetGroup = companyFleetGroup;
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

    public List<CompanyGroup> getCompanyGroups() {
        return companyGroups;
    }

    public void setCompanyGroups(List<CompanyGroup> companyGroups) {
        this.companyGroups = companyGroups;
    }

    public void addCompanyGroup(CompanyGroup companyGroup){
        this.companyGroups.add(companyGroup);
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicleList.add(vehicle);
    }

    public CompanyFleetGroup getCompanyFleetGroup() {
        return companyFleetGroup;
    }

    public void setCompanyFleetGroup(CompanyFleetGroup companyFleetGroup) {
        this.companyFleetGroup = companyFleetGroup;
    }

    @Override
    public String toString() {
        return "CompanyDistrictGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyFleetGroup=" + companyFleetGroup +
                '}';
    }
}
