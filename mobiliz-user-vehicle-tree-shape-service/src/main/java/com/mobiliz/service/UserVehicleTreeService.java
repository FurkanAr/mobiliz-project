package com.mobiliz.service;

import com.mobiliz.client.GlobalVehicleClient;
import com.mobiliz.client.response.VehicleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserVehicleTreeService {

    private final GlobalVehicleClient vehicleClient;

    public UserVehicleTreeService(GlobalVehicleClient vehicleClient) {
        this.vehicleClient = vehicleClient;
    }

    public List<VehicleResponse> getUserVehicles(String header) {


         List<VehicleResponse> vehicleResponse = vehicleClient.getUserVehiclesTree(header);


         return vehicleResponse;
    }
}
