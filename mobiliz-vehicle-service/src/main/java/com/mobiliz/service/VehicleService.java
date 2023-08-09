package com.mobiliz.service;

import com.mobiliz.converter.VehicleConverter;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.exception.vehicle.VehicleNotFoundException;
import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.model.CompanyGroup;
import com.mobiliz.model.Vehicle;
import com.mobiliz.repository.VehicleRepository;
import com.mobiliz.request.VehicleRequest;
import com.mobiliz.response.VehicleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CompanyGroupService companyGroupService;
    private final CompanyGroupDistrictService companyGroupDistrictService;
    private final VehicleConverter vehicleConverter;
    private final CompanyService companyService;
    private final CompanyFleetGroupService companyFleetGroupService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public VehicleService(VehicleRepository vehicleRepository, CompanyGroupService companyGroupService, CompanyGroupDistrictService companyGroupDistrictService, VehicleConverter vehicleConverter, CompanyService companyService, CompanyFleetGroupService companyFleetGroupService) {
        this.vehicleRepository = vehicleRepository;
        this.companyGroupService = companyGroupService;
        this.companyGroupDistrictService = companyGroupDistrictService;
        this.vehicleConverter = vehicleConverter;
        this.companyService = companyService;
        this.companyFleetGroupService = companyFleetGroupService;
    }

    public List<VehicleResponse> findAllVehicles() {
        return vehicleConverter.convert(vehicleRepository.findAll());
    }

    public VehicleResponse createVehicle(VehicleRequest vehicleRequest) {
        logger.info("createVehicle method started");
        logger.info("VehicleRequest: {}", vehicleRequest);

        Vehicle vehicle = vehicleConverter.convert(vehicleRequest);

        CompanyGroup companyGroup = null;
        if (vehicleRequest.getCompanyGroupId() != null) {
            companyGroup = companyGroupService.getCompanyGroupById(vehicleRequest.getCompanyGroupId());
        }
        vehicle.setCompanyGroup(companyGroup);
        vehicle.setCompany(companyService.getCompanyById(vehicleRequest.getCompanyId()));
        vehicle.setCompanyFleetGroup(companyFleetGroupService.getCompanyFleetGroupById(vehicleRequest.getCompanyFleetGroupId()));
        vehicle.setCompanyDistrictGroup(companyGroupDistrictService
                .getCompanyDistrictGroupById(vehicleRequest.getCompanyDistrictGroupId()));
        vehicle = vehicleRepository.save(vehicle);
        logger.info("vehicle created: {}", vehicle);

        logger.info("createVehicle method successfully worked");
        return vehicleConverter.convert(vehicle);
    }

    public VehicleResponse getByVehicleId(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()
                -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS + vehicleId));
        return vehicleConverter.convert(vehicle);
    }

    public List<Vehicle> getByCompanyGroupId(Long id) {
        return vehicleRepository.findAllByCompanyGroupId(id);
    }

    public List<Vehicle> getCompanyDistrictGroupById(Long id) {
        return vehicleRepository.findAllByCompanyDistrictGroupId(id);
    }
}
