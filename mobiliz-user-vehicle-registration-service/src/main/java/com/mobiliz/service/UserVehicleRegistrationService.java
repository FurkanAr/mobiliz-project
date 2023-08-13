package com.mobiliz.service;

import com.mobiliz.client.VehicleServiceClient;
import org.springframework.stereotype.Service;

@Service
public class UserVehicleRegistrationService {

    private final VehicleServiceClient vehicleServiceClient;

    public UserVehicleRegistrationService(VehicleServiceClient vehicleServiceClient) {
        this.vehicleServiceClient = vehicleServiceClient;
    }

    public String addVehicleToUser(String header, Long vehicleId) {
        return vehicleServiceClient.addVehicleToUserByVehicleId(header, vehicleId);
    }


    public String addCompanyGroupVehiclesToUser(String header, Long companyGroupId) {
        return vehicleServiceClient.addVehicleToUserByCompanyGroupId(header, companyGroupId);
    }

    public String addCompanyDistrictGroupVehiclesToUser(String header, Long fleetId, Long districtgroupId) {
        return vehicleServiceClient.addVehicleToUserByCompanyDistrictGroupId(header, fleetId, districtgroupId);
    }
}
