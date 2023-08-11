package com.mobiliz.service;

import com.mobiliz.converter.VehicleConverter;
import com.mobiliz.exception.companyFleetGroup.CompanyIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.exception.vehicle.VehicleNotFoundException;
import com.mobiliz.model.*;
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
    private final CompanyDistrictGroupService companyGroupDistrictService;
    private final VehicleConverter vehicleConverter;
    private final CompanyService companyService;
    private final CompanyFleetGroupService companyFleetGroupService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public VehicleService(VehicleRepository vehicleRepository, CompanyGroupService companyGroupService, CompanyDistrictGroupService companyGroupDistrictService, VehicleConverter vehicleConverter, CompanyService companyService, CompanyFleetGroupService companyFleetGroupService) {
        this.vehicleRepository = vehicleRepository;
        this.companyGroupService = companyGroupService;
        this.companyGroupDistrictService = companyGroupDistrictService;
        this.vehicleConverter = vehicleConverter;
        this.companyService = companyService;
        this.companyFleetGroupService = companyFleetGroupService;
    }

    public VehicleResponse createVehicle(Long adminId, VehicleRequest vehicleRequest) {
        logger.info("createVehicle method started");
        Company company = companyService.getCompanyById(vehicleRequest.getCompanyId());

        CompanyFleetGroup companyFleetGroup = companyFleetGroupService
                .getCompanyFleetGroupById(vehicleRequest.getCompanyFleetGroupId());

        CompanyDistrictGroup companyDistrictGroup = companyGroupDistrictService
                .getCompanyDistrictGroupById(vehicleRequest.getCompanyDistrictGroupId());


        checkAdminMatch(adminId, vehicleRequest.getCompanyId());
        checkAdminMatch(adminId, companyFleetGroup.getCompany().getId());
        checkAdminMatch(adminId, companyDistrictGroup.getCompany().getId());


        if (vehicleRequest.getCompanyGroupId() != null) {
            CompanyGroup companyGroup = companyGroupService.getCompanyGroupById(vehicleRequest.getCompanyGroupId());
            checkAdminMatch(adminId, companyGroup.getCompany().getId());
        }

        logger.info("VehicleRequest: {}", vehicleRequest);

        Vehicle vehicle = vehicleConverter.convert(vehicleRequest);

        vehicle.setCompany(company);
        vehicle.setCompanyFleetGroup(companyFleetGroup);
        vehicle.setCompanyDistrictGroup(companyDistrictGroup);
        vehicle.setCompanyGroup(companyGroup);

        vehicle = vehicleRepository.save(vehicle);
        logger.info("vehicle created: {}", vehicle);

        logger.info("createVehicle method successfully worked");
        return vehicleConverter.convert(vehicle);
    }

    public VehicleResponse getByVehicleId(Long adminId, Long vehicleId) {
        Company company = companyService.findCompanyByAdminId(adminId);
        Vehicle vehicle = vehicleRepository.findByIdAndCompanyId(vehicleId, company.getId());
        return vehicleConverter.convert(vehicle);
    }


    public List<VehicleResponse> getCompanyVehicles(Long adminId) {
        Company company = companyService.findCompanyByAdminId(adminId);

        List<Vehicle> vehicles = vehicleRepository.findAllByCompanyId(company.getId()).orElseThrow(()
                -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_ID + company.getId()));

        return vehicleConverter.convert(vehicles);
    }

    public Vehicle getVehicleById(Long vehicleId){
        return vehicleRepository.findById(vehicleId).orElseThrow(()
                -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS + vehicleId));
    }

    public List<Vehicle> getByCompanyGroupId(Long id) {
        return vehicleRepository.findAllByCompanyGroupId(id);
    }

    public List<Vehicle> getCompanyDistrictGroupById(Long id) {
        return vehicleRepository.findAllByCompanyDistrictGroupId(id);
    }



}
