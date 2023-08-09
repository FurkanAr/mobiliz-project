package com.mobiliz.response;

import com.mobiliz.model.CompanyGroup;
import com.mobiliz.model.Vehicle;

import java.util.List;

public class CompanyDistrictGroupResponse {

    private Long id;
    private String name;
    private List<CompanyGroupResponse> companyGroupResponses;
    private List<VehicleResponse> vehicleResponses;

    public CompanyDistrictGroupResponse() {
    }

    public CompanyDistrictGroupResponse(Long id, String name, List<CompanyGroupResponse> companyGroupResponses,
                                                List<VehicleResponse> vehicleResponses) {
        this.id = id;
        this.name = name;
        this.companyGroupResponses = companyGroupResponses;
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

    public List<CompanyGroupResponse> getCompanyGroupResponses() {
        return companyGroupResponses;
    }

    public void setCompanyGroupResponses(List<CompanyGroupResponse> companyGroupResponses) {
        this.companyGroupResponses = companyGroupResponses;
    }

    public List<VehicleResponse> getVehicleResponses() {
        return vehicleResponses;
    }

    public void setVehicleResponses(List<VehicleResponse> vehicleResponses) {
        this.vehicleResponses = vehicleResponses;
    }

    @Override
    public String toString() {
        return "CompanyDistrictGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
