package com.mobiliz.service;

import com.mobiliz.client.CompanyDistrictGroupServiceClient;
import com.mobiliz.client.CompanyGroupClient;
import com.mobiliz.client.request.UserCompanyDistrictGroupSaveRequest;
import com.mobiliz.client.request.UserCompanyGroupSaveRequest;
import com.mobiliz.client.response.CompanyGroupResponse;
import com.mobiliz.client.response.VehicleResponseStatus;
import com.mobiliz.constant.Constants;
import com.mobiliz.converter.VehicleConverter;
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
        String userName = findUserNameByHeaderToken(header);
        String userSurname = findUserSurNameByHeaderToken(header);
        Long companyId = findCompanyIdByHeaderToken(header);

        Vehicle vehicleFoundById = checkVehicleIsExist(vehicleId);

        checkVehicleIdAndCompanyIdMatch(vehicleId, companyId);

        if (checkUserHasVehicle(userId, vehicleFoundById)) {
            logger.warn("User already has vehicle: {}", userId);
            return Constants.USER_HAS_ALREADY_VEHICLE + vehicleFoundById.getId();
        }

        Vehicle vehicleFoundByIdAndCompanyIdAndStatus = vehicleRepository.findByIdAndCompanyIdAndStatusAvailable(
                vehicleId, companyId, VehicleStatus.AVAILABLE.name()).orElseThrow(
                () -> new VehicleNotAvailableException(Messages.Vehicle.VEHICLE_IN_USE_EXCEPTION + vehicleId));

        logger.info("vehicleFoundByIdAndCompanyIdAndStatus : {}", vehicleFoundByIdAndCompanyIdAndStatus);

        saveVehicleAndVehicleRecord(vehicleFoundByIdAndCompanyIdAndStatus, userId, userName, userSurname);
        logger.info("addVehicleToUser method finished");
        return Constants.VEHICLE_IDENTIFIED_TO_USER;
    }

    public String addVehicleToUserByCompanyGroupId(String header, Long companyGroupId) {
        logger.info("addVehicleToUser method started");
        Long userId = findUserIdByHeaderToken(header);
        String userName = findUserNameByHeaderToken(header);
        String userSurname = findUserSurNameByHeaderToken(header);
        Long companyId = findCompanyIdByHeaderToken(header);

        List<Vehicle> vehicleFoundByCompanyId = checkVehicleIsExistByCompanyId(companyId);

        vehicleFoundByCompanyId.forEach(vehicle -> logger.info("vehicleFoundByCompanyId: {}", vehicle));

        List<Vehicle> vehiclesFoundByCompanyGroupId = checkVehicleIsExistByCompanyGroupId(companyGroupId);

        vehiclesFoundByCompanyGroupId.forEach(vehicle -> logger.info("vehiclesFoundByCompanyGroupId: {}", vehicle));

        List<Vehicle> vehiclesFoundByCompanyIdAndGroupId = vehicleRepository.
                findAllByCompanyIdAndCompanyGroupId(companyId, companyGroupId)
                .orElseThrow(() ->
                        new VehicleNotFoundException(
                                Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_GROUP_ID
                                        + companyGroupId));

        vehiclesFoundByCompanyIdAndGroupId.forEach(vehicle -> logger.info("vehiclesFoundByCompanyIdAndGroupId: {}", vehicle));


        for (Vehicle vehicle : vehiclesFoundByCompanyIdAndGroupId) {
            if (userId.equals(vehicle.getUserId())) {
                logger.warn("User already has vehicle: {}", userId);
                return Constants.USER_HAS_ALREADY_VEHICLE + vehicle.getId();
            }
            logger.info("Found vehicle by companyId and groupId: {}", vehicle);
        }

        List<Vehicle> vehicles = vehicleRepository
                .findAllByCompanyIdAndCompanyGroupIdAndStatusAvailable(
                        companyId, companyGroupId, VehicleStatus.AVAILABLE.name())
                .orElseThrow(() ->
                        new VehicleNotFoundException(
                                Constants.VEHICLE_NOT_FOUND_BY_GIVEN_COMPANY_GROUP_ID + companyGroupId));

        vehicles.forEach(vehicle -> System.out.println("---vehicle inside company group: " + vehicle));

        UserCompanyGroupSaveRequest request = new UserCompanyGroupSaveRequest(userId, userName, userSurname);
        VehicleResponseStatus responseStatus = companyGroupClient
                .saveCompanyGroupUser(header, companyGroupId, request);

        switch (responseStatus) {
            case REJECTED:
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLES_NOT_IDENTIFIED_TO_USER;
            case USER_ALREADY_HAS:
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLES_USER_ALREADY_HAS;
            default:
                logger.info("addVehicleToUser method finished");
                // saveVehiclesAndVehicleRecords(vehicles, userId, userName, userSurname);
                vehicles.forEach(vehicle -> addVehicleToUserByVehicleId(header, vehicle.getId()));
                //
                return Constants.VEHICLES_IDENTIFIED_TO_USER;
        }
    }


    public String addVehicleToUserByCompanyDistrictGroupId(String header, Long fleetId, Long districtGroupId) {
        logger.info("addVehicleToUser method started");
        Long userId = findUserIdByHeaderToken(header);
        String userName = findUserNameByHeaderToken(header);
        String userSurname = findUserSurNameByHeaderToken(header);
        Long companyId = findCompanyIdByHeaderToken(header);
        String role = findUserRoleByHeaderToken(header);


        List<Vehicle> vehicles = vehicleRepository
                .findAllByCompanyIdAndCompanyDistrictGroupIdAndStatusAvailable(
                        companyId, districtGroupId, VehicleStatus.AVAILABLE.name())
                .orElseThrow(() ->
                        new VehicleNotFoundException(Constants.VEHICLE_NOT_FOUND_BY_GIVEN_COMPANY_DISTRICT_GROUP_ID + districtGroupId));

        vehicles.forEach(vehicle -> System.out.println("------vehicle inside company district group: " + vehicle));

        List<CompanyGroupResponse> companyGroupResponses = companyGroupClient
                .getCompanyGroupsByDistrictGroupId(header, fleetId, districtGroupId);

/*
        companyGroupResponses.forEach(companyGroupResponse ->
                addVehicleToUserByCompanyGroupId(header, fleetId, districtGroupId, companyGroupResponse.getId()));


 */
        saveVehiclesAndVehicleRecords(vehicles, userId, userName, userSurname);

        UserCompanyDistrictGroupSaveRequest districtGroupSaveRequest =
                new UserCompanyDistrictGroupSaveRequest(userId, userName, userSurname);

        VehicleResponseStatus vehicleResponseStatus = companyDistrictGroupServiceClient
                .saveCompanyDistrictGroupUser(header, districtGroupId, districtGroupSaveRequest);

        switch (vehicleResponseStatus) {
            case REJECTED:
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLES_NOT_IDENTIFIED_TO_USER;
            case USER_ALREADY_HAS:
                logger.info("addVehicleToUser method finished");
                return Constants.VEHICLES_USER_ALREADY_HAS;
            default:
                logger.info("addVehicleToUser method finished");
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

    private boolean checkUserHasVehicle(Long userId, Vehicle vehicle) {
        return userId.equals(vehicle.getUserId());
    }

    private void checkVehicleIdAndCompanyIdMatch(Long vehicleId, Long companyId) {
        Vehicle vehicleFoundByIdAndCompanyId = vehicleRepository.findByIdAndCompanyId(vehicleId, companyId).orElseThrow(
                () -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS_BY_GIVEN_COMPANY_ID + companyId));
        logger.info("vehicleFoundByIdAndCompanyId : {}", vehicleFoundByIdAndCompanyId);
    }

    private Vehicle checkVehicleIsExist(Long vehicleId) {
        Vehicle vehicleFoundById = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(Messages.Vehicle.NOT_EXISTS + vehicleId));

        logger.info("vehicleFoundById : {}", vehicleFoundById);
        return vehicleFoundById;
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