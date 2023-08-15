package com.mobiliz.service;

import com.mobiliz.client.CompanyDistrictGroupServiceClient;
import com.mobiliz.client.CompanyGroupClient;
import com.mobiliz.client.response.CompanyDistrictGroupResponse;
import com.mobiliz.client.response.CompanyGroupResponse;
import com.mobiliz.converter.VehicleConverter;
import com.mobiliz.exception.Permission.UserHasNotPermissionException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.exception.vehicle.VehicleNotFoundException;
import com.mobiliz.model.Vehicle;
import com.mobiliz.repository.VehicleRepository;
import com.mobiliz.request.VehicleRequest;
import com.mobiliz.response.VehicleResponse;
import com.mobiliz.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleConverter vehicleConverter;
    private final CompanyGroupClient companyGroupClient;
    private final CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient;
    private final JwtTokenService jwtTokenService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public VehicleService(VehicleRepository vehicleRepository, VehicleConverter vehicleConverter, CompanyGroupClient companyGroupClient, CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient, JwtTokenService jwtTokenService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleConverter = vehicleConverter;
        this.companyGroupClient = companyGroupClient;
        this.companyDistrictGroupServiceClient = companyDistrictGroupServiceClient;
        this.jwtTokenService = jwtTokenService;
    }


    public List<VehicleResponse> getCompanyVehicles(String header) {
        logger.info("getCompanyVehicles method started");
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehicles = getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);

        logger.info("companyId : {}", companyId);
        logger.info("getCompanyVehicles method finished");
        return vehicleConverter.convert(vehicles);
    }


    public VehicleResponse getByVehicleId(String header, Long vehicleId) {
        logger.info("getByVehicleId method started");
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehicles = getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);
        logger.info("vehicles : {}", vehicles);

        Vehicle vehicle = vehicleRepository
                .findByIdAndCompanyIdAndCompanyFleetId(vehicleId, companyId, companyFleetId).orElseThrow(
                () -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS + vehicleId));

        logger.info("vehicle : {}", vehicle);

        checkCompanyDistrictGroupResponse(companyId, vehicle);

        logger.info("getByVehicleId method finished");
        return vehicleConverter.convert(vehicle);
    }



    public VehicleResponse createVehicle(String header,
                                         Long districtGroupId, Optional<Long> companyGroupId,
                                         VehicleRequest vehicleRequest) {
        logger.info("createVehicle method started");

        Long companyId = findCompanyIdByHeaderToken(header);
        String companyName = findCompanyNameByHeaderToken(header);

        logger.info("VehicleRequest: {}", vehicleRequest);

        Vehicle vehicle = vehicleConverter.convert(vehicleRequest);

        if (companyGroupId.isEmpty()) {
            CompanyDistrictGroupResponse companyDistrictGroupResponse = companyDistrictGroupServiceClient
                    .getCompanyDistrictGroupsByFleetIdAndDistrictId(header, districtGroupId);

            checkCompanyDistrictGroupResponse(companyId, companyDistrictGroupResponse);
            vehicle = saveVehicleByCompanyDistrictGroup(companyId, companyName, vehicle, companyDistrictGroupResponse);

            vehicle = vehicleRepository.save(vehicle);
            logger.info("vehicle created: {}", vehicle);

            logger.info("createVehicle method successfully worked");
            return vehicleConverter.convert(vehicle);

        }

        CompanyGroupResponse companyGroupResponse = companyGroupClient
                .getCompanyGroupByDistrictGroupIAndFleetIdAndCompanyGroupId(
                        header, districtGroupId, companyGroupId.get());
        logger.info("companyGroupResponse : {}", companyGroupResponse);
        logger.info("companyId : {}", companyId);

        checkCompanyGroupResponse(companyId, companyGroupResponse);

        Vehicle vehicleResponse = saveVehicleByCompanyGroup(companyId, companyName, vehicle, companyGroupResponse);

        Vehicle savedVehicle = vehicleRepository.save(vehicleResponse);
        logger.info("vehicle created: {}", savedVehicle);

        logger.info("createVehicle method successfully worked");
        return vehicleConverter.convert(savedVehicle);
    }


    private Vehicle  saveVehicleByCompanyGroup(Long companyId, String companyName, Vehicle vehicle, CompanyGroupResponse companyGroupResponse) {
        logger.info("saveVehicleByCompanyGroup method started");
        vehicle.setCompanyId(companyId);
        vehicle.setCompanyName(companyName);
        vehicle.setCompanyFleetId(companyGroupResponse.getCompanyFleetGroupId());
        vehicle.setCompanyFleetName(companyGroupResponse.getCompanyFleetGroupName());
        vehicle.setCompanyDistrictGroupId(companyGroupResponse.getId());
        vehicle.setCompanyDistrictGroupName(companyGroupResponse.getName());
        vehicle.setCompanyGroupId(companyGroupResponse.getId());
        vehicle.setCompanyGroupName(companyGroupResponse.getName());
        logger.info("saveVehicleByCompanyGroup method successfully worked");
        return vehicle;
    }


    private  Vehicle saveVehicleByCompanyDistrictGroup(Long companyId, String companyName, Vehicle vehicle, CompanyDistrictGroupResponse companyDistrictGroupResponse) {
        logger.info("saveVehicleByCompanyDistrictGroup method started");
        vehicle.setCompanyId(companyId);
        vehicle.setCompanyName(companyName);
        vehicle.setCompanyFleetId(companyDistrictGroupResponse.getCompanyFleetGroupId());
        vehicle.setCompanyFleetName(companyDistrictGroupResponse.getCompanyFleetGroupName());
        vehicle.setCompanyDistrictGroupId(companyDistrictGroupResponse.getId());
        vehicle.setCompanyDistrictGroupName(companyDistrictGroupResponse.getName());
        logger.info("saveVehicleByCompanyDistrictGroup method successfully worked");
        return vehicle;
    }
    private List<Vehicle> getVehiclesByCompanyIdAndCompanyFleetId(Long companyId, Long companyFleetId) {
        logger.info("getVehiclesByCompanyIdAndCompanyFleetId method started");

        List<Vehicle> vehicles = vehicleRepository.findAllByCompanyIdAndCompanyFleetId(companyId, companyFleetId)
                .orElseThrow(() ->
                        new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_ID + companyId));

        logger.info("vehicles: {}", vehicles);
        logger.info("getVehiclesByCompanyIdAndCompanyFleetId method successfully worked");
        return vehicles;
    }

    private  void checkCompanyGroupResponse(Long companyId, CompanyGroupResponse companyGroupResponse) {
        logger.info("checkCompanyGroupResponse method started");
        if (!companyId.equals(companyGroupResponse.getCompanyId())) {
            logger.warn("User has not permission: {}", companyId);
            throw new UserHasNotPermissionException(Messages.Vehicle.USER_HAS_NO_PERMIT);
        }
        logger.info("checkCompanyGroupResponse method successfully worked");

    }

    private  void checkCompanyDistrictGroupResponse(Long companyId, Vehicle vehicle) {
        logger.info("checkCompanyDistrictGroupResponse method started");
        if (!companyId.equals(vehicle.getCompanyId())) {
            logger.warn("User has not permission to vehicle: {}", vehicle);
            throw new UserHasNotPermissionException(Messages.Vehicle.USER_HAS_NO_PERMIT);
        }
        logger.info("checkCompanyDistrictGroupResponse method successfully worked");
    }

    private void checkCompanyDistrictGroupResponse(Long companyId, CompanyDistrictGroupResponse companyDistrictGroupResponse) {
        logger.info("checkCompanyDistrictGroupResponse method started");
        if (!companyId.equals(companyDistrictGroupResponse.getCompanyId())) {
            logger.warn("User has not permission: {}", companyId);
            throw new UserHasNotPermissionException(Messages.Vehicle.USER_HAS_NO_PERMIT);
        }
        logger.info("checkCompanyDistrictGroupResponse method successfully worked");
    }

    private Long findCompanyIdByHeaderToken(String header) {
        logger.info("findCompanyIdByHeaderToken method started");
        String token = header.substring(7);
        Long companyId = Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
        logger.info("companyId : {}", companyId);
        logger.info("findCompanyIdByHeaderToken method finished");
        return companyId;
    }

    private Long findCompanyFleetIdByHeaderToken(String header) {
        logger.info("findCompanyFleetIdByHeaderToken method started");
        String token = header.substring(7);
        Long companyFleetId = Long.valueOf(jwtTokenService.getClaims(token).get("companyFleetId").toString());
        logger.info("companyFleetId : {}", companyFleetId);
        logger.info("findCompanyFleetIdByHeaderToken method finished");
        return companyFleetId;
    }

    private String findCompanyNameByHeaderToken(String header) {
        logger.info("findCompanyNameByHeaderToken method started");
        String token = header.substring(7);
        String companyName = jwtTokenService.getClaims(token).get("companyName").toString();
        logger.info("companyName : {}", companyName);
        logger.info("findCompanyNameByHeaderToken method finished");
        return companyName;
    }


}
