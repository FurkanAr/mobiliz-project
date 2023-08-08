package com.mobiliz.response;


public class VehicleResponse {

    private String id;
    private String licencePlate;
    private String vehicleIdentificationNumber;
    private String label;
    private String brand;
    private String model;
    private String modelYear;
    private String status;
    private String companyGroupId;
    private String companyDistrictGroupId;

    public VehicleResponse() {
    }

    public VehicleResponse(String id, String licencePlate, String vehicleIdentificationNumber, String label, String brand,
                           String model, String modelYear, String status, String companyGroupId, String companyDistrictGroupId) {
        this.id = id;
        this.licencePlate = licencePlate;
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.label = label;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.status = status;
        this.companyGroupId = companyGroupId;
        this.companyDistrictGroupId = companyDistrictGroupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyGroupId() {
        return companyGroupId;
    }

    public void setCompanyGroupId(String companyGroupId) {
        this.companyGroupId = companyGroupId;
    }

    public String getCompanyDistrictGroupId() {
        return companyDistrictGroupId;
    }

    public void setCompanyDistrictGroupId(String companyDistrictGroupId) {
        this.companyDistrictGroupId = companyDistrictGroupId;
    }

    @Override
    public String toString() {
        return "VehicleResponse{" +
                "id=" + id +
                ", licencePlate='" + licencePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", companyGroupId=" + companyGroupId +
                ", companyDistrictGroupId=" + companyDistrictGroupId +
                '}';
    }
}
