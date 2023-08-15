package com.mobiliz.service;

import com.mobiliz.client.GlobalVehicleClient;
import com.mobiliz.client.response.VehicleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserVehicleTreeService {

    private final GlobalVehicleClient vehicleClient;
    Logger logger = LoggerFactory.getLogger(getClass());

    public UserVehicleTreeService(GlobalVehicleClient vehicleClient) {
        this.vehicleClient = vehicleClient;
    }

    public List<VehicleResponse> getUserVehicles(String header) {

        logger.info("getUserVehicles method started");
         List<VehicleResponse> vehicleResponse = vehicleClient.getUserVehiclesTree(header);
        logger.info("vehicleResponse : {}", vehicleResponse);
        logger.info("getUserVehicles method finished");
         return vehicleResponse;
    }
}
