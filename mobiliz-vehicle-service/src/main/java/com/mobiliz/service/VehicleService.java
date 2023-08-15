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
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehicles = getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);

        return vehicleConverter.convert(vehicles);
    }


    public VehicleResponse getByVehicleId(String header, Long vehicleId) {
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehicles = getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);

        Vehicle vehicle = vehicleRepository
                .findByIdAndCompanyIdAndCompanyFleetId(vehicleId, companyId, companyFleetId).orElseThrow(
                () -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS + vehicleId));

        checkCompanyDistrictGroupResponse(companyId, vehicle);

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

        vehicle = saveVehicleByCompanyGroup(companyId, companyName, vehicle, companyGroupResponse);

        vehicle = vehicleRepository.save(vehicle);
        logger.info("vehicle created: {}", vehicle);

        logger.info("createVehicle method successfully worked");
        return vehicleConverter.convert(vehicle);
    }


    private Vehicle  saveVehicleByCompanyGroup(Long companyId, String companyName, Vehicle vehicle, CompanyGroupResponse companyGroupResponse) {
        vehicle.setCompanyId(companyId);
        vehicle.setCompanyName(companyName);
        vehicle.setCompanyFleetId(companyGroupResponse.getCompanyFleetGroupId());
        vehicle.setCompanyFleetName(companyGroupResponse.getCompanyFleetGroupName());
        vehicle.setCompanyDistrictGroupId(companyGroupResponse.getId());
        vehicle.setCompanyDistrictGroupName(companyGroupResponse.getName());
        vehicle.setCompanyGroupId(companyGroupResponse.getId());
        vehicle.setCompanyGroupName(companyGroupResponse.getName());
        return vehicle;
    }


    private  Vehicle saveVehicleByCompanyDistrictGroup(Long companyId, String companyName, Vehicle vehicle, CompanyDistrictGroupResponse companyDistrictGroupResponse) {
        vehicle.setCompanyId(companyId);
        vehicle.setCompanyName(companyName);
        vehicle.setCompanyFleetId(companyDistrictGroupResponse.getCompanyFleetGroupId());
        vehicle.setCompanyFleetName(companyDistrictGroupResponse.getCompanyFleetGroupName());
        vehicle.setCompanyDistrictGroupId(companyDistrictGroupResponse.getId());
        vehicle.setCompanyDistrictGroupName(companyDistrictGroupResponse.getName());
        return vehicle;
    }
    private List<Vehicle> getVehiclesByCompanyIdAndCompanyFleetId(Long companyId, Long companyFleetId) {
        List<Vehicle> vehicles = vehicleRepository.findAllByCompanyIdAndCompanyFleetId(companyId, companyFleetId).orElseThrow(()
                -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_ID + companyId));
        return vehicles;
    }

    private static void checkCompanyGroupResponse(Long companyId, CompanyGroupResponse companyGroupResponse) {
        if (!companyId.equals(companyGroupResponse.getCompanyId())) {
            throw new UserHasNotPermissionException(Messages.Vehicle.USER_HAS_NO_PERMIT);
        }
    }

    private static void checkCompanyDistrictGroupResponse(Long companyId, Vehicle vehicle) {
        if (!companyId.equals(vehicle.getCompanyId())) {
            throw new UserHasNotPermissionException(Messages.Vehicle.USER_HAS_NO_PERMIT);
        }
    }

    private static void checkCompanyDistrictGroupResponse(Long companyId, CompanyDistrictGroupResponse companyDistrictGroupResponse) {
        if (!companyId.equals(companyDistrictGroupResponse.getCompanyId())) {
            throw new UserHasNotPermissionException(Messages.Vehicle.USER_HAS_NO_PERMIT);
        }
    }

    private Long findCompanyFleetIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyFleetId").toString());
    }

    private Long findCompanyIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
    }

    private String findCompanyNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyName").toString();
    }


}
