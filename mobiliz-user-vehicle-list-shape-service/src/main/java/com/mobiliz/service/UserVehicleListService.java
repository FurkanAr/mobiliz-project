package com.mobiliz.service;

import com.mobiliz.client.GlobalVehicleClient;
import com.mobiliz.client.response.VehicleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserVehicleListService {

    private final GlobalVehicleClient vehicleClient;
    Logger logger = LoggerFactory.getLogger(getClass());

    public UserVehicleListService(GlobalVehicleClient vehicleClient) {
        this.vehicleClient = vehicleClient;
    }

    public List<VehicleResponse> getUserVehicles(String header) {
        logger.info("getUserVehicles method started");
        List<VehicleResponse> vehicleResponses = vehicleClient.getUserVehiclesList(header);
        logger.info("vehicleResponses : {}", vehicleResponses);
        logger.info("getUserVehicles method finished");
        return vehicleResponses;
    }
}
