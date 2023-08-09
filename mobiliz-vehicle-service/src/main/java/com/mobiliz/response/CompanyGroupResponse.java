package com.mobiliz.response;

import java.util.List;

public class CompanyGroupResponse {

    private Long id;
    private String name;
    private List<VehicleResponse> vehicleResponses;

    public CompanyGroupResponse() {
    }

    public CompanyGroupResponse(Long id, String name, List<VehicleResponse> vehicleResponses) {
        this.id = id;
        this.name = name;
        this.vehicleResponses = vehicleResponses;
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

    public List<VehicleResponse> getVehicleResponses() {
        return vehicleResponses;
    }

    public void setVehicleResponses(List<VehicleResponse> vehicleResponses) {
        this.vehicleResponses = vehicleResponses;
    }

    @Override
    public String toString() {
        return "CompanyGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
