package com.mobiliz.service;

import com.mobiliz.client.VehicleServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserVehicleRegistrationService {

    private final VehicleServiceClient vehicleServiceClient;
    Logger logger = LoggerFactory.getLogger(getClass());

    public UserVehicleRegistrationService(VehicleServiceClient vehicleServiceClient) {
        this.vehicleServiceClient = vehicleServiceClient;
    }

    public String addVehicleToUser(String header, Long vehicleId) {
        logger.info("addVehicleToUser method started");
        String response = vehicleServiceClient.addVehicleToUserByVehicleId(header, vehicleId);
        logger.info("response : {}", response);
        logger.info("addVehicleToUser method finished");
        return response;
    }


    public String addCompanyGroupVehiclesToUser(String header, Long companyGroupId, Long districtGroupId) {
        logger.info("addCompanyGroupVehiclesToUser method started");
        String response = vehicleServiceClient.addVehicleToUserByCompanyGroupId(header, companyGroupId, districtGroupId);
        logger.info("response : {}", response);
        logger.info("addCompanyGroupVehiclesToUser method finished");
        return response;
    }

    public String addCompanyDistrictGroupVehiclesToUser(String header, Long districtGroupId) {
        logger.info("addCompanyDistrictGroupVehiclesToUser method started");
        String response = vehicleServiceClient.addVehicleToUserByCompanyDistrictGroupId(header, districtGroupId);
        logger.info("response : {}", response);
        logger.info("addCompanyDistrictGroupVehiclesToUser method finished");
        return response;
    }
}
