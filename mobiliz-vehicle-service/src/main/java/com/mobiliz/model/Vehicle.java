package com.mobiliz.model;

import com.mobiliz.model.enums.VehicleStatus;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "licence_plate")
    private String licencePlate;
    @Column(name = "vehicle_identification_number", nullable = true)
    private String vehicleIdentificationNumber;
    @Column(name = "label", nullable = true)
    private String label;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "model_year")
    private String modelYear;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "company_group_id")
    private CompanyGroup companyGroup;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "company_district_group_id")
    private CompanyDistrictGroup companyDistrictGroup;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "company_fleet_group_id")
    private CompanyFleetGroup companyFleetGroup;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "sur_name")
    private String surName;

    public Vehicle() {
    }

    public Vehicle(String licencePlate, String brand, String model, String modelYear, VehicleStatus status) {
        this.licencePlate = licencePlate;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }

    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public CompanyGroup getCompanyGroup() {
        return companyGroup;
    }

    public void setCompanyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
    }

    public CompanyDistrictGroup getCompanyDistrictGroup() {
        return companyDistrictGroup;
    }

    public void setCompanyDistrictGroup(CompanyDistrictGroup companyDistrictGroup) {
        this.companyDistrictGroup = companyDistrictGroup;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CompanyFleetGroup getCompanyFleetGroup() {
        return companyFleetGroup;
    }

    public void setCompanyFleetGroup(CompanyFleetGroup companyFleetGroup) {
        this.companyFleetGroup = companyFleetGroup;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", licencePlate='" + licencePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
