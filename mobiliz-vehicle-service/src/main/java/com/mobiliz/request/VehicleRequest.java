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

    public VehicleRequest() {
    }

    public VehicleRequest(String licencePlate, String brand, String model, String modelYear, Long companyDistrictGroupId) {
        this.licencePlate = licencePlate;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.companyDistrictGroupId = companyDistrictGroupId;
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
