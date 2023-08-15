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
    @Column(name = "company_group_id")
    private Long companyGroupId;
    @Column(name = "company_group_name")
    private String companyGroupName;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "sur_name")
    private String surName;
    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private VehicleRecord vehicleRecord;

    public Vehicle() {
        this.status = VehicleStatus.AVAILABLE;
    }

    public Vehicle(String licencePlate, String brand, String model, String modelYear) {
        this.licencePlate = licencePlate;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.status = VehicleStatus.AVAILABLE;
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

    public Long getCompanyGroupId() {
        return companyGroupId;
    }

    public void setCompanyGroupId(Long companyGroupId) {
        this.companyGroupId = companyGroupId;
    }

    public String getCompanyGroupName() {
        return companyGroupName;
    }

    public void setCompanyGroupName(String companyGroupName) {
        this.companyGroupName = companyGroupName;
    }

    public VehicleRecord getVehicleRecord() {
        return vehicleRecord;
    }

    public void setVehicleRecord(VehicleRecord vehicleRecord) {
        this.vehicleRecord = vehicleRecord;
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
