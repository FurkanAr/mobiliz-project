package com.mobiliz.service;

import com.mobiliz.client.GlobalVehicleClient;
import com.mobiliz.client.response.VehicleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserVehicleListService {

    private final GlobalVehicleClient vehicleClient;

    public UserVehicleListService(GlobalVehicleClient vehicleClient) {
        this.vehicleClient = vehicleClient;
    }

    public List<VehicleResponse> getUserVehicles(String header) {
        return vehicleClient.getUserVehiclesList(header);
    }
}
