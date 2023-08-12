package com.mobiliz.converter;

import com.mobiliz.constant.Constants;
import com.mobiliz.model.Vehicle;
import com.mobiliz.model.enums.VehicleStatus;
import com.mobiliz.request.VehicleRequest;
import com.mobiliz.response.VehicleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleConverter {

    Logger logger = LoggerFactory.getLogger(getClass());

    public VehicleResponse convert(Vehicle vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setId(vehicle.getId());
        vehicleResponse.setLicencePlate(vehicle.getLicencePlate());
        vehicleResponse.setBrand(vehicle.getBrand());
        vehicleResponse.setModel(vehicle.getModel());
        vehicleResponse.setModelYear(vehicle.getModelYear());
        vehicleResponse.setLabel(vehicle.getLabel());
        vehicleResponse.setVehicleIdentificationNumber(vehicle.getVehicleIdentificationNumber());
        vehicleResponse.setCompanyId(vehicle.getCompanyId());
        vehicleResponse.setCompanyName(vehicle.getCompanyName());
        vehicleResponse.setCompanyFleetGroupId(vehicle.getCompanyFleetId());
        vehicleResponse.setCompanyFleetGroupName(vehicle.getCompanyFleetName());
        vehicleResponse.setCompanyDistrictGroupId(vehicle.getCompanyDistrictGroupId());
        vehicleResponse.setCompanyDistrictGroupName(vehicle.getCompanyDistrictGroupName());
        vehicleResponse.setCompanyGroupId(vehicle.getCompanyGroupId());
        vehicleResponse.setCompanyGroupName(vehicle.getCompanyGroupName());
        return vehicleResponse;
    }

    public Vehicle convert(VehicleRequest vehicleRequest) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicencePlate(vehicleRequest.getLicencePlate());

        if (vehicleRequest.getVehicleIdentificationNumber() != null) {
            vehicle.setVehicleIdentificationNumber(vehicleRequest.getVehicleIdentificationNumber());
        }else{
            vehicle.setVehicleIdentificationNumber(Constants.NO_INFO);
        }

        if (vehicleRequest.getLabel() != null) {
            vehicle.setLabel(vehicleRequest.getLabel());
        }
        else{
            vehicle.setLabel(Constants.NO_INFO);
        }

        vehicle.setBrand(vehicleRequest.getBrand());
        vehicle.setModel(vehicleRequest.getModel());
        vehicle.setModelYear(vehicleRequest.getModelYear());
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        return vehicle;
    }

    public List<VehicleResponse> convert(List<Vehicle> vehicleList) {
        logger.info("convert vehicleList to VehicleResponse method started");
        List<VehicleResponse> vehicleResponses = new ArrayList<>();
        vehicleList.forEach(vehicle -> vehicleResponses.add(convert(vehicle)));
        logger.info("convert vehicleList to VehicleResponse method successfully worked");
        return vehicleResponses;
    }
}
