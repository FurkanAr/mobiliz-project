package com.mobiliz.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company_group")
public class CompanyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "companyGroup", cascade = CascadeType.ALL)
    private List<Vehicle> vehicleList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "company_district_group_id")
    private CompanyDistrictGroup companyDistrictGroup;

    public CompanyGroup() {
    }

    public CompanyGroup(String name, List<Vehicle> vehicleList, CompanyDistrictGroup companyDistrictGroup) {
        this.name = name;
        this.vehicleList = vehicleList;
        this.companyDistrictGroup = companyDistrictGroup;
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

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicleList.add(vehicle);
    }

    public CompanyDistrictGroup getCompanyDistrictGroup() {
        return companyDistrictGroup;
    }

    public void setCompanyDistrictGroup(CompanyDistrictGroup companyDistrictGroup) {
        this.companyDistrictGroup = companyDistrictGroup;
    }

    @Override
    public String toString() {
        return "CompanyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyDistrictGroup=" + companyDistrictGroup +
                '}';
    }
}
