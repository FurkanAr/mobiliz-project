package com.mobiliz.service;

import com.mobiliz.client.CompanyDistrictGroupServiceClient;
import com.mobiliz.client.CompanyGroupClient;
import com.mobiliz.client.request.UserCompanyDistrictGroupSaveRequest;
import com.mobiliz.client.request.UserCompanyGroupSaveRequest;
import com.mobiliz.client.response.CompanyGroupResponse;
import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.constant.Constants;
import com.mobiliz.converter.VehicleConverter;
import com.mobiliz.exception.Permission.UserHasNotPermissionException;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.exception.vehicle.VehicleNotAvailableException;
import com.mobiliz.exception.vehicle.VehicleNotFoundException;
import com.mobiliz.model.Vehicle;
import com.mobiliz.model.VehicleRecord;
import com.mobiliz.model.enums.VehicleStatus;
import com.mobiliz.repository.VehicleRecordRepository;
import com.mobiliz.repository.VehicleRepository;
import com.mobiliz.response.VehicleResponse;
import com.mobiliz.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalVehicleService {

    private final JwtTokenService jwtTokenService;
    private final VehicleRepository vehicleRepository;
    private final VehicleConverter vehicleConverter;
    private final VehicleRecordRepository vehicleRecordRepository;
    private final CompanyGroupClient companyGroupClient;
    private final CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient;
    Logger logger = LoggerFactory.getLogger(getClass());

    public GlobalVehicleService(JwtTokenService jwtTokenService, VehicleRepository vehicleRepository, VehicleConverter vehicleConverter, VehicleRecordRepository vehicleRecordRepository, CompanyGroupClient companyGroupClient, CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient) {
        this.jwtTokenService = jwtTokenService;
        this.vehicleRepository = vehicleRepository;
        this.vehicleConverter = vehicleConverter;
        this.vehicleRecordRepository = vehicleRecordRepository;
        this.companyGroupClient = companyGroupClient;
        this.companyDistrictGroupServiceClient = companyDistrictGroupServiceClient;
    }

    public List<VehicleResponse> getCompanyVehicles(String header) {
        Long companyId = findCompanyIdByHeaderToken(header);

        List<Vehicle> vehicles = vehicleRepository.findAllByCompanyId(companyId).orElseThrow(()
                -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_ID + companyId));

        return vehicleConverter.convert(vehicles);
    }

    public String addVehicleToUserByVehicleId(String header, Long vehicleId) {
        logger.info("addVehicleToUser method started");
        Long userId = findUserIdByHeaderToken(header);
        System.out.println("userId: " + userId);
        String userName = findUserNameByHeaderToken(header);
        System.out.println("userName: " + userName);
        String userSurname = findUserSurNameByHeaderToken(header);
        System.out.println("userSurname: " + userSurname);
        Long companyId = findCompanyIdByHeaderToken(header);
        System.out.println("companyId: " + companyId);
        String role = findUserRoleByHeaderToken(header);

        if (Constants.ADMIN.equals(role)) {
            throw new UserHasNotPermissionException(Messages.Vehicle.ADMIN_CANNOT_IDENTIFY_VEHICLE + userId);
        }

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS + vehicleId));
        logger.info("foundVehicle : {}", vehicle);

        if (!companyId.equals(vehicle.getCompanyId())) {
            throw new UserHasNotPermissionException(Messages.Vehicle.USER_HAS_NO_PERMIT);
        }

        if (!VehicleStatus.AVAILABLE.equals(vehicle.getStatus())) {
            throw new VehicleNotAvailableException(Messages.Vehicle.VEHICLE_IN_USE_EXCEPTION + vehicleId);
        }

        vehicle.setUserId(userId);
        vehicle.setFirstName(userName);
        vehicle.setSurName(userSurname);
        vehicle.setStatus(VehicleStatus.IN_USE);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        logger.info("vehicle : {}", savedVehicle);
        VehicleRecord vehicleRecord = new VehicleRecord(userId, userName, userSurname);
        vehicleRecord.setVehicle(savedVehicle);
        vehicleRecordRepository.save(vehicleRecord);
        logger.info("addVehicleToUser method finished");
        return Constants.VEHICLE_IDENTIFIED_TO_USER;
    }

    public String addVehicleToUserByCompanyGroupId(String header, Long companyGroupId) {
        logger.info("addVehicleToUser method started");
        Long userId = findUserIdByHeaderToken(header);
        System.out.println("userId: " + userId);
        String userName = findUserNameByHeaderToken(header);
        System.out.println("userName: " + userName);
        String userSurname = findUserSurNameByHeaderToken(header);
        System.out.println("userSurname: " + userSurname);
        Long companyId = findCompanyIdByHeaderToken(header);
        System.out.println("companyId: " + companyId);
        String role = findUserRoleByHeaderToken(header);

        if (Constants.ADMIN.equals(role)) {
            throw new UserHasNotPermissionException(Messages.Vehicle.ADMIN_CANNOT_IDENTIFY_VEHICLE + userId);
        }

        List<Vehicle> vehicles = vehicleRepository.findAllByCompanyGroupId(companyGroupId).orElseThrow(
                () -> new VehicleNotFoundException(Constants.VEHICLE_NOT_FOUND_BY_GIVEN_COMPANY_GROUP_ID + companyGroupId));


        vehicles.forEach(vehicle -> {
            if (!vehicle.getCompanyId().equals(companyId)) {
                throw new UserHasNotPermissionException(Messages.Vehicle.USER_HAS_NO_PERMIT);
            }
        });

        vehicles.forEach(vehicle -> {
            if (!vehicle.getStatus().equals(VehicleStatus.AVAILABLE)) {
                throw new VehicleNotAvailableException(Messages.Vehicle.VEHICLE_IN_USE_EXCEPTION + vehicle.getId());
            }
        });

        vehicles.forEach(vehicle -> {
            vehicle.setUserId(userId);
            vehicle.setFirstName(userName);
            vehicle.setSurName(userSurname);
            vehicle.setStatus(VehicleStatus.IN_USE);
            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            logger.info("vehicle : {}", savedVehicle);
            VehicleRecord vehicleRecord = new VehicleRecord(userId, userName, userSurname);
            vehicleRecord.setVehicle(savedVehicle);
            vehicleRecordRepository.save(vehicleRecord);
        });

        UserCompanyGroupSaveRequest request = new UserCompanyGroupSaveRequest(userId, userName, userSurname);
        VehicleResponseStatus responseStatus = companyGroupClient.saveCompanyGroupUser(header, companyGroupId, request);
        if (VehicleResponseStatus.REJECTED.equals(responseStatus)) {
            return Constants.VEHICLES_NOT_IDENTIFIED_TO_USER;
        }
        logger.info("addVehicleToUser method finished");
        return Constants.VEHICLES_IDENTIFIED_TO_USER;
    }

    public String addVehicleToUserByCompanyDistrictGroupId(String header, Long districtGroupId) {
        logger.info("addVehicleToUser method started");
        Long userId = findUserIdByHeaderToken(header);
        System.out.println("userId: " + userId);
        String userName = findUserNameByHeaderToken(header);
        System.out.println("userName: " + userName);
        String userSurname = findUserSurNameByHeaderToken(header);
        System.out.println("userSurname: " + userSurname);
        Long companyId = findCompanyIdByHeaderToken(header);
        System.out.println("companyId: " + companyId);
        String role = findUserRoleByHeaderToken(header);

        if (Constants.ADMIN.equals(role)) {
            throw new UserHasNotPermissionException(Messages.Vehicle.ADMIN_CANNOT_IDENTIFY_VEHICLE + userId);
        }

        List<Vehicle> vehicles = vehicleRepository.findAllByCompanyDistrictGroupId(districtGroupId).orElseThrow(
                () -> new VehicleNotFoundException(Constants.VEHICLE_NOT_FOUND_BY_GIVEN_COMPANY_DISTRICT_GROUP_ID + districtGroupId));


        vehicles.forEach(vehicle -> {
            if (!vehicle.getCompanyId().equals(companyId)) {
                throw new UserHasNotPermissionException(Messages.Vehicle.USER_HAS_NO_PERMIT);
            }
        });

        vehicles.forEach(vehicle -> {
            if (!vehicle.getStatus().equals(VehicleStatus.AVAILABLE)) {
                throw new VehicleNotAvailableException(Messages.Vehicle.VEHICLE_IN_USE_EXCEPTION + vehicle.getId());
            }
        });

        vehicles.forEach(vehicle -> {
            vehicle.setUserId(userId);
            vehicle.setFirstName(userName);
            vehicle.setSurName(userSurname);
            vehicle.setStatus(VehicleStatus.IN_USE);
            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            logger.info("vehicle : {}", savedVehicle);
            VehicleRecord vehicleRecord = new VehicleRecord(userId, userName, userSurname);
            vehicleRecord.setVehicle(savedVehicle);
            vehicleRecordRepository.save(vehicleRecord);
        });

        List<CompanyGroupResponse> companyGroupResponses = companyGroupClient
                .getCompanyGroupsByDistrictGroupId(header, districtGroupId);

        companyGroupResponses.forEach(companyGroupResponse ->
                addVehicleToUserByCompanyGroupId(header, companyGroupResponse.getId()));

        UserCompanyDistrictGroupSaveRequest districtGroupSaveRequest =
                new UserCompanyDistrictGroupSaveRequest(userId, userName, userSurname);

        VehicleResponseStatus vehicleResponseStatus = companyDistrictGroupServiceClient
                .saveCompanyDistrictGroupUser(header, districtGroupId, districtGroupSaveRequest);

        if (VehicleResponseStatus.REJECTED.equals(vehicleResponseStatus)) {
            return Constants.VEHICLES_NOT_IDENTIFIED_TO_USER;
        }

        logger.info("addVehicleToUser method finished");
        return Constants.VEHICLES_IDENTIFIED_TO_USER;
    }

    private Long findCompanyIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("companyId").toString());
    }

    private Long findUserIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("userId").toString());
    }

    private String findUserNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("name").toString();
    }

    private String findUserSurNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("surname").toString();
    }

    private String findCompanyNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyName").toString();
    }

    private String findUserRoleByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("role").toString();
    }


}
