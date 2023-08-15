package com.mobiliz.service;

import com.mobiliz.client.CompanyDistrictGroupServiceClient;
import com.mobiliz.client.CompanyGroupClient;
import com.mobiliz.client.response.CompanyDistrictCompanyGroupResponse;
import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.constant.Constants;
import com.mobiliz.converter.VehicleConverter;
import com.mobiliz.exception.messages.Messages;
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
import java.util.Optional;

@Service
public class GlobalVehicleService {

    private final JwtTokenService jwtTokenService;
    private final VehicleRepository vehicleRepository;
    private final VehicleConverter vehicleConverter;
    private final VehicleRecordRepository vehicleRecordRepository;
    private final CompanyGroupClient companyGroupClient;
    private final CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient;
    Logger logger = LoggerFactory.getLogger(getClass());

    public GlobalVehicleService(JwtTokenService jwtTokenService, VehicleRepository vehicleRepository,
                                VehicleConverter vehicleConverter, VehicleRecordRepository vehicleRecordRepository,
                                CompanyGroupClient companyGroupClient,
                                CompanyDistrictGroupServiceClient companyDistrictGroupServiceClient) {
        this.jwtTokenService = jwtTokenService;
        this.vehicleRepository = vehicleRepository;
        this.vehicleConverter = vehicleConverter;
        this.vehicleRecordRepository = vehicleRecordRepository;
        this.companyGroupClient = companyGroupClient;
        this.companyDistrictGroupServiceClient = companyDistrictGroupServiceClient;
    }

    public List<VehicleResponse> getCompanyVehicles(String header) {
        logger.info("getCompanyVehicles method started");
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehicles = getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);

        logger.info("vehicles : {}", vehicles);
        logger.info("getCompanyVehicles method finished");
        return vehicleConverter.convert(vehicles);
    }

    public List<VehicleResponse> getUserVehiclesList(String header) {
        logger.info("getUserVehiclesList method started");
        Long userId = findUserIdByHeaderToken(header);
        List<Vehicle> vehicles = getUserVehicles(userId);
        logger.info("vehicles : {}", vehicles);
        logger.info("getUserVehiclesList method finished");
        return vehicleConverter.convert(vehicles);
    }

    public List<VehicleResponse> getUserVehiclesTree(String header) {
        logger.info("getUserVehiclesTree method started");
        Long userId = findUserIdByHeaderToken(header);

        List<Vehicle> vehicles = getUserVehicles(userId);
        logger.info("vehicles : {}", vehicles);
        logger.info("getUserVehiclesTree method finished");
        return vehicleConverter.convert(vehicles);
    }


    public String addVehicleToUserByVehicleId(String header, Long vehicleId) {
        logger.info("addVehicleToUserByVehicleId method started");
        Long userId = findUserIdByHeaderToken(header);
        String userName = findUserNameByHeaderToken(header);
        String userSurname = findUserSurNameByHeaderToken(header);
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehicles = getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);
        logger.info("vehicles : {}", vehicles);

        Optional<Vehicle> foundVehicle = vehicleRepository.findByIdAndCompanyIdAndCompanyFleetId(
                vehicleId, companyId, companyFleetId);

        if (foundVehicle.isEmpty()) {
            logger.warn("Vehicle can't found with given id: {}", vehicleId);

            return Constants.VEHICLE_NOT_FOUND_BY_GIVEN_ID;
        }

        logger.info("foundVehicle : {}", foundVehicle.get());

        if (VehicleStatus.IN_USE.equals(foundVehicle.get().getStatus())) {
            if (userId.equals(foundVehicle.get().getUserId())) {
                logger.warn("User already has vehicle: {}", foundVehicle.get());
                return Constants.USER_HAS_ALREADY_VEHICLE + foundVehicle.get().getId();
            }
            logger.warn("Vehicle not available: {}", foundVehicle.get());
            return Constants.VEHICLE_NOT_AVAILABLE;
        }

        saveVehicleAndVehicleRecord(foundVehicle.get(), userId, userName, userSurname);
        logger.info("addVehicleToUserByVehicleId method finished");
        return Constants.VEHICLE_IDENTIFIED_TO_USER;
    }

    public String addVehicleToUserByCompanyGroupId(String header, Long companyGroupId, Long districtGroupId) {
        logger.info("addVehicleToUserByCompanyGroupId method started");
        Long userId = findUserIdByHeaderToken(header);
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehiclesFoundByCompanyAndCompanyFleet =
                getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);

        logger.info("vehiclesFoundByCompanyAndCompanyFleet : {}", vehiclesFoundByCompanyAndCompanyFleet);

        List<Vehicle> vehiclesFoundByCompanyDistrictAndCompanyFleetAndCompany =
                getVehiclesFoundByCompanyDistrictAndCompanyFleetAndCompany(districtGroupId,
                        companyId, companyFleetId);

        logger.info("vehiclesFoundByCompanyDistrictAndCompanyFleetAndCompany : {}",
                vehiclesFoundByCompanyDistrictAndCompanyFleetAndCompany);


        List<Vehicle> foundVehicles = getVehiclesByCompanyGroupId(companyGroupId, districtGroupId, companyId, companyFleetId);

        logger.info("foundVehicles : {}", foundVehicles);

        for (Vehicle vehicle : foundVehicles) {
            if (VehicleStatus.IN_USE.equals(vehicle.getStatus())) {
                if (userId.equals(vehicle.getUserId())) {
                    logger.warn("User already has vehicle: {}", userId);
                    return Constants.USER_HAS_ALREADY_VEHICLE + vehicle.getId();
                }
                logger.warn("Vehicle not available: {}", vehicle);
                return Constants.VEHICLE_NOT_AVAILABLE;
            }
        }

        VehicleResponseStatus responseStatus = companyGroupClient
                .saveCompanyGroupUser(header, companyGroupId, districtGroupId);

        logger.info("responseStatus : {}", responseStatus.toString());

        switch (responseStatus) {
            case COMPANY_GROUP_NOT_FOUND:
                logger.info("addVehicleToUserByCompanyGroupId method finished");
                return Constants.VEHICLES_COMPANY_GROUP_NOT_FOUND + companyGroupId;
            case USER_ALREADY_HAS:
                logger.info("addVehicleToUserByCompanyGroupId method finished");
                return Constants.VEHICLES_USER_ALREADY_HAS;
            case NOT_AVAILABLE:
                logger.info("addVehicleToUserByCompanyGroupId method finished");
                return Constants.VEHICLE_NOT_AVAILABLE;
            default:
                foundVehicles.forEach(vehicle -> addVehicleToUserByVehicleId(header, vehicle.getId()));
                logger.info("foundVehicles : {}", foundVehicles);
                logger.info("addVehicleToUserByCompanyGroupId method finished");
                return Constants.VEHICLES_IDENTIFIED_TO_USER;
        }
    }

    private List<Vehicle> getVehiclesByCompanyGroupId(Long companyGroupId, Long districtGroupId, Long companyId, Long companyFleetId) {
        logger.info("getVehiclesByCompanyGroupId method started");

        List<Vehicle> foundVehicles = vehicleRepository.
                findByCompanyGroupIdAndCompanyDistrictGroupIdAndCompanyFleetIdAndCompanyId(
                        companyGroupId, districtGroupId, companyFleetId, companyId)
                .orElseThrow(() ->
                        new VehicleNotFoundException(
                                Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_GROUP_ID
                                        + companyGroupId));
        logger.info("foundVehicles : {}", foundVehicles);
        logger.info("getVehiclesByCompanyGroupId method finished");
        return foundVehicles;
    }


    public String addVehicleToUserByCompanyDistrictGroupId(String header, Long districtGroupId) {
        logger.info("addVehicleToUserByCompanyDistrictGroupId method started");
        Long userId = findUserIdByHeaderToken(header);
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehiclesFoundByCompanyAndCompanyFleet =
                getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);

        logger.info("vehiclesFoundByCompanyAndCompanyFleet : {}", vehiclesFoundByCompanyAndCompanyFleet);

        List<Vehicle> vehicles = vehicleRepository.
                findAllByCompanyDistrictGroupIdAndCompanyFleetGroupIdAndCompanyId(districtGroupId, companyFleetId, companyId)
                .orElseThrow(() ->
                        new VehicleNotFoundException(
                                Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_DISTRICT_GROUP_ID
                                        + districtGroupId));

        logger.info("vehicles : {}", vehicles);

        for (Vehicle vehicle : vehicles) {
            if (VehicleStatus.IN_USE.equals(vehicle.getStatus())) {
                if (userId.equals(vehicle.getUserId())) {
                    logger.warn("User already has vehicle: {}", vehicle);
                    return Constants.USER_HAS_ALREADY_VEHICLE + vehicle.getId();
                }
                logger.warn("Vehicle not available: {}", vehicle);
                return Constants.VEHICLE_NOT_AVAILABLE;
            }
        }

        List<CompanyDistrictCompanyGroupResponse> companyGroupResponses = companyGroupClient
                .getCompanyGroupsByFleetGroupId(header, districtGroupId);
        logger.info("companyGroupResponses : {}", companyGroupResponses);

        companyGroupResponses.forEach(companyGroupResponse ->
                addVehicleToUserByCompanyGroupId(header, companyGroupResponse.getId(), districtGroupId));

        VehicleResponseStatus vehicleResponseStatus = companyDistrictGroupServiceClient
                .saveCompanyDistrictGroupUser(header, districtGroupId);
        logger.info("vehicleResponseStatus : {}", vehicleResponseStatus.toString());

        switch (vehicleResponseStatus) {
            case DISTRICT_NOT_FOUND:
                logger.info("addVehicleToUserByCompanyDistrictGroupId method finished");
                return Constants.VEHICLE_NOT_FOUND_BY_GIVEN_COMPANY_DISTRICT_GROUP_ID;
            case USER_ALREADY_HAS:
                logger.info("addVehicleToUserByCompanyDistrictGroupId method finished");
                return Constants.VEHICLES_USER_ALREADY_HAS;
            case NOT_AVAILABLE:
                logger.info("addVehicleToUserByCompanyDistrictGroupId method finished");
                return Constants.VEHICLE_NOT_AVAILABLE;
            default:
                vehicles.forEach(vehicle -> addVehicleToUserByVehicleId(header, vehicle.getId()));
                logger.info("addVehicleToUserByCompanyDistrictGroupId method finished");
                return Constants.VEHICLES_IDENTIFIED_TO_USER;
        }

    }

    private void saveVehicleAndVehicleRecord(Vehicle vehicle, Long userId, String userName, String userSurname) {
        logger.info("saveVehicleAndVehicleRecord method started");
        vehicle.setUserId(userId);
        vehicle.setFirstName(userName);
        vehicle.setSurName(userSurname);
        vehicle.setStatus(VehicleStatus.IN_USE);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        logger.info("savedVehicle : {}", savedVehicle);

        Optional<VehicleRecord> vehicleRecord = vehicleRecordRepository.findByVehicleId(savedVehicle.getId());
        if (vehicleRecord.isEmpty()) {
            VehicleRecord record = new VehicleRecord(userId, userName, userSurname);
            record.setVehicle(savedVehicle);
            logger.info("saved record : {}", record);
            vehicleRecordRepository.save(record);
        }
        logger.info("saveVehicleAndVehicleRecord method finished");

    }


    private List<Vehicle> getVehiclesFoundByCompanyDistrictAndCompanyFleetAndCompany(
            Long districtGroupId,
            Long companyId,
            Long companyFleetId) {
        logger.info("getVehiclesFoundByCompanyDistrictAndCompanyFleetAndCompany method started");

        List<Vehicle> vehicles = vehicleRepository.
                findAllByCompanyDistrictGroupIdAndCompanyFleetGroupIdAndCompanyId(districtGroupId, companyFleetId, companyId)
                .orElseThrow(() ->
                        new VehicleNotFoundException(
                                Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_DISTRICT_GROUP_ID
                                        + districtGroupId));
        logger.info("vehicles : {}", vehicles);
        logger.info("getVehiclesFoundByCompanyDistrictAndCompanyFleetAndCompany method finished");
        return vehicles;
    }

    private List<Vehicle> getVehiclesByCompanyIdAndCompanyFleetId(Long companyId, Long companyFleetId) {
        logger.info("getVehiclesByCompanyIdAndCompanyFleetId method started");

        List<Vehicle> vehicles = vehicleRepository.findAllByCompanyIdAndCompanyFleetId(companyId, companyFleetId)
                .orElseThrow(()  ->
                        new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_ID + companyId));
        logger.info("vehicles : {}", vehicles);
        logger.info("getVehiclesByCompanyIdAndCompanyFleetId method started");
        return vehicles;
    }


    private List<Vehicle> getUserVehicles(Long userId) {
        logger.info("getUserVehicles method started");
        List<Vehicle> vehicles = vehicleRepository.findAllByUserId(userId).orElseThrow(
                () -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXIST_BY_GIVEN_USER_ID + userId));
        logger.info("vehicles : {}", vehicles);
        logger.info("getUserVehicles method started");
        return vehicles;
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


    private Long findUserIdByHeaderToken(String header) {
        logger.info("findUserIdByHeaderToken method started");
        String token = header.substring(7);
        Long userId  = Long.valueOf(jwtTokenService.getClaims(token).get("userId").toString());
        logger.info("userId : {}", userId);
        logger.info("findUserIdByHeaderToken method finished");
        return userId;
    }

    private String findUserNameByHeaderToken(String header) {
        logger.info("findUserNameByHeaderToken method started");
        String token = header.substring(7);
        String userName = jwtTokenService.getClaims(token).get("name").toString();
        logger.info("userName : {}", userName);
        logger.info("findUserNameByHeaderToken method finished");
        return userName;
    }

    private String findUserSurNameByHeaderToken(String header) {
        logger.info("findUserSurNameByHeaderToken method started");
        String token = header.substring(7);
        String userSurname = jwtTokenService.getClaims(token).get("surname").toString();
        logger.info("userSurname : {}", userSurname);
        logger.info("findUserSurNameByHeaderToken method finished");
        return userSurname;
    }


}
