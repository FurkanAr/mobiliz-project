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
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehicles = getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);

        return vehicleConverter.convert(vehicles);
    }

    public List<VehicleResponse> getUserVehiclesList(String header) {
        Long userId = findUserIdByHeaderToken(header);

        List<Vehicle> vehicles = getUserVehicles(userId);

        return vehicleConverter.convert(vehicles);
    }

    public List<VehicleResponse> getUserVehiclesTree(String header) {
        Long userId = findUserIdByHeaderToken(header);

        List<Vehicle> vehicles = getUserVehicles(userId);

        return vehicleConverter.convert(vehicles);
    }


    public String addVehicleToUserByVehicleId(String header, Long vehicleId) {
        logger.info("addVehicleToUser method started");
        Long userId = findUserIdByHeaderToken(header);
        String userName = findUserNameByHeaderToken(header);
        String userSurname = findUserSurNameByHeaderToken(header);
        Long companyId = findCompanyIdByHeaderToken(header);
        Long companyFleetId = findCompanyFleetIdByHeaderToken(header);

        List<Vehicle> vehicles = getVehiclesByCompanyIdAndCompanyFleetId(companyId, companyFleetId);

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
            return Constants.VEHICLE_NOT_AVAILABLE;
        }

        saveVehicleAndVehicleRecord(foundVehicle.get(), userId, userName, userSurname);
        logger.info("addVehicleToUser method finished");
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
                return Constants.VEHICLE_NOT_AVAILABLE;
            }

        }

        VehicleResponseStatus responseStatus = companyGroupClient
                .saveCompanyGroupUser(header, companyGroupId, districtGroupId);

        logger.info("responseStatus : {}", responseStatus.toString());

        switch (responseStatus) {
            case COMPANY_GROUP_NOT_FOUND:
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLES_COMPANY_GROUP_NOT_FOUND + companyGroupId;
            case USER_ALREADY_HAS:
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLES_USER_ALREADY_HAS;
            case NOT_AVAILABLE:
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLE_NOT_AVAILABLE;
            default:
                logger.info("addVehicleToUser method finished");
                // saveVehiclesAndVehicleRecords(vehicles, userId, userName, userSurname);
                foundVehicles.forEach(vehicle -> addVehicleToUserByVehicleId(header, vehicle.getId()));
                return Constants.VEHICLES_IDENTIFIED_TO_USER;
        }
    }

    private List<Vehicle> getVehiclesByCompanyGroupId(Long companyGroupId, Long districtGroupId, Long companyId, Long companyFleetId) {
        List<Vehicle> foundVehicles = vehicleRepository.
                findByCompanyGroupIdAndCompanyDistrictGroupIdAndCompanyFleetIdAndCompanyId(
                        companyGroupId, districtGroupId, companyFleetId, companyId)
                .orElseThrow(() ->
                        new VehicleNotFoundException(
                                Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_GROUP_ID
                                        + companyGroupId));
        return foundVehicles;
    }


    public String addVehicleToUserByCompanyDistrictGroupId(String header, Long districtGroupId) {
        logger.info("addVehicleToUser method started");
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
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLE_NOT_FOUND_BY_GIVEN_COMPANY_DISTRICT_GROUP_ID;
            case USER_ALREADY_HAS:
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLES_USER_ALREADY_HAS;
            case NOT_AVAILABLE:
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLE_NOT_AVAILABLE;
            default:
                logger.info("addVehicleToUser method finished");
                vehicles.forEach(vehicle -> addVehicleToUserByVehicleId(header, vehicle.getId()));
                return Constants.VEHICLES_IDENTIFIED_TO_USER;
        }

    }

    private void saveVehiclesAndVehicleRecords(List<Vehicle> vehicles, Long userId, String userName, String userSurname) {
        vehicles.forEach(vehicle -> {
            saveVehicleAndVehicleRecord(vehicle, userId, userName, userSurname);
        });
    }

    private void saveVehicleAndVehicleRecord(Vehicle vehicle, Long userId, String userName, String userSurname) {
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
            vehicleRecordRepository.save(record);
        }

    }


    private List<Vehicle> getVehiclesFoundByCompanyDistrictAndCompanyFleetAndCompany(
            Long districtGroupId,
            Long companyId,
            Long companyFleetId) {

        return vehicleRepository.
                findAllByCompanyDistrictGroupIdAndCompanyFleetGroupIdAndCompanyId(districtGroupId, companyFleetId, companyId)
                .orElseThrow(() ->
                        new VehicleNotFoundException(
                                Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_DISTRICT_GROUP_ID
                                        + districtGroupId));
    }

    private List<Vehicle> getVehiclesByCompanyIdAndCompanyFleetId(Long companyId, Long companyFleetId) {
        List<Vehicle> vehicles = vehicleRepository.findAllByCompanyIdAndCompanyFleetId(companyId, companyFleetId).orElseThrow(()
                -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_ID + companyId));
        return vehicles;
    }

    private List<Vehicle> checkVehicleIsExistByCompanyId(Long companyId) {
        List<Vehicle> vehicleFoundByCompanyId = vehicleRepository.findAllByCompanyGroupId(companyId)
                .orElseThrow(() -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_ID + companyId));
        vehicleFoundByCompanyId.forEach(vehicle -> logger.info("vehicleFoundByCompanyId: {}", vehicle));
        return vehicleFoundByCompanyId;
    }


    private List<Vehicle> checkVehicleIsExistByCompanyGroupId(Long companyGroupId) {
        List<Vehicle> vehicleFoundByCompanyGroupId = vehicleRepository.findAllByCompanyGroupId(companyGroupId)
                .orElseThrow(() -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_GROUP_ID + companyGroupId));
        vehicleFoundByCompanyGroupId.forEach(vehicle -> logger.info("vehicleFoundByCompanyGroupId: {}", vehicle));
        return vehicleFoundByCompanyGroupId;
    }


    private Vehicle getVehicleByIdAndCompanyAndCompanyFleet(Long vehicleId, Long companyId, Long companyFleetId) {
        Vehicle foundVehicle = vehicleRepository.findByIdAndCompanyIdAndCompanyFleetId(
                        vehicleId, companyId, companyFleetId)
                .orElseThrow(()
                        -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS + vehicleId));
        logger.info("vehicleFoundByIdAndCompanyId : {}", foundVehicle);
        return foundVehicle;

    }

    private Vehicle checkVehicleIsExist(Long vehicleId) {
        Vehicle vehicleFoundById = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS + vehicleId));

        logger.info("vehicleFoundById : {}", vehicleFoundById);
        return vehicleFoundById;
    }

    private List<Vehicle> getUserVehicles(Long userId) {
        List<Vehicle> vehicles = vehicleRepository.findAllByUserId(userId).orElseThrow(
                () -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXIST_BY_GIVEN_USER_ID + userId));
        return vehicles;
    }



    private Long findUserIdByHeaderToken(String header) {
        String token = header.substring(7);
        return Long.valueOf(jwtTokenService.getClaims(token).get("userId").toString());
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

    private String findCompanyFleetNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("companyFleetName").toString();
    }

    private String findUserNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("name").toString();
    }

    private String findUserSurNameByHeaderToken(String header) {
        String token = header.substring(7);
        return jwtTokenService.getClaims(token).get("surname").toString();
    }



}
