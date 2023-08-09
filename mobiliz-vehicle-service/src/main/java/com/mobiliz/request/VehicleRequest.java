package com.mobiliz.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VehicleRequest {
    @NotEmpty(message = "Please enter  licencePlate")
    private String licencePlate;
    private String vehicleIdentificationNumber;
    private String label;
    @NotEmpty(message = "Please enter brand")
    private String brand;
    @NotEmpty(message = "Please enter model")
    private String model;
    @NotEmpty(message = "Please enter modelYear")
    private String modelYear;
    private Long companyGroupId;
    @NotNull(message = "Please enter companyDistrictGroupId")
    private Long companyDistrictGroupId;
    @NotNull(message = "Please enter company id")
    private Long companyId;
    @NotNull(message = "Please enter company fleet group id")
    private Long companyFleetGroupId;

    public VehicleRequest() {
    }

    public VehicleRequest(String licencePlate, String brand, String model, String modelYear,
                          Long companyDistrictGroupId, Long companyId, Long companyFleetGroupId) {
        this.licencePlate = licencePlate;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.companyDistrictGroupId = companyDistrictGroupId;
        this.companyId = companyId;
        this.companyFleetGroupId = companyFleetGroupId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Long getCompanyGroupId() {
        return companyGroupId;
    }

    public void setCompanyGroupId(Long companyGroupId) {
        this.companyGroupId = companyGroupId;
    }

    public Long getCompanyDistrictGroupId() {
        return companyDistrictGroupId;
    }

    public void setCompanyDistrictGroupId(Long companyDistrictGroupId) {
        this.companyDistrictGroupId = companyDistrictGroupId;
    }

    public Long getCompanyFleetGroupId() {
        return companyFleetGroupId;
    }

    public void setCompanyFleetGroupId(Long companyFleetGroupId) {
        this.companyFleetGroupId = companyFleetGroupId;
    }

    @Override
    public String toString() {
        return "VehicleRequest{" +
                "licencePlate='" + licencePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", modelYear='" + modelYear + '\'' +
                '}';
    }
}
