package com.mobiliz.response;


public class VehicleResponse {

    private Long id;
    private String licencePlate;
    private String brand;
    private String model;
    private String modelYear;

    public VehicleResponse() {
    }

    public VehicleResponse(Long id, String licencePlate, String brand, String model, String modelYear) {
        this.id = id;
        this.licencePlate = licencePlate;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
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


    @Override
    public String toString() {
        return "VehicleResponse{" +
                "id=" + id +
                ", licencePlate='" + licencePlate + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
