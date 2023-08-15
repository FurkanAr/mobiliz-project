package com.mobiliz.client.response;


public class VehicleResponse {

    private Long id;
    private String licencePlate;
    private String vehicleIdentificationNumber;
    private String label;
    private String brand;
    private String model;
    private String modelYear;
    private Long companyGroupId;
    private String companyGroupName;
    private Long companyDistrictGroupId;
    private String companyDistrictGroupName;
    private Long companyId;
    private String companyName;
    private Long companyFleetGroupId;
    private String companyFleetGroupName;


    public VehicleResponse() {
    }

    public VehicleResponse(Long id, String licencePlate, String brand, String model, String modelYear,
                           Long companyDistrictGroupId, String companyDistrictGroupName, Long companyId,
                           String companyName, Long companyFleetGroupId, String companyFleetGroupName) {
        this.id = id;
        this.licencePlate = licencePlate;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.companyDistrictGroupId = companyDistrictGroupId;
        this.companyDistrictGroupName = companyDistrictGroupName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyFleetGroupId = companyFleetGroupId;
        this.companyFleetGroupName = companyFleetGroupName;
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

    public Long getCompanyFleetGroupId() {
        return companyFleetGroupId;
    }

    public void setCompanyFleetGroupId(Long companyFleetGroupId) {
        this.companyFleetGroupId = companyFleetGroupId;
    }

    public String getCompanyFleetGroupName() {
        return companyFleetGroupName;
    }

    public void setCompanyFleetGroupName(String companyFleetGroupName) {
        this.companyFleetGroupName = companyFleetGroupName;
    }

    @Override
    public String toString() {
        return "VehicleResponse{" +
                "id=" + id +
                ", licencePlate='" + licencePlate + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
