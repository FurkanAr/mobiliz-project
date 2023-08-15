package com.mobiliz.repository;

import com.mobiliz.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<List<Vehicle>> findAllByCompanyGroupId(Long id);

    Optional<List<Vehicle>> findAllByCompanyDistrictGroupId(Long id);

    @Query(value = "SElECT * FROM Vehicle WHERE company_district_group_id = :id AND status = :status",
            nativeQuery = true)
    Optional<List<Vehicle>> findAllByCompanyDistrictGroupIdAndStatusAvailable(@Param("id") Long id,
                                                                              @Param("status") String AVAILABLE);

    Optional<List<Vehicle>> findAllByCompanyId(Long id);

    @Query(value = "SElECT * FROM Vehicle WHERE id = :id AND company_id = :companyId", nativeQuery = true)
    Optional<Vehicle> findByIdAndCompanyId(@Param("id") Long id,
                                           @Param("companyId") Long companyId);


    @Query(value = "SElECT * FROM Vehicle  WHERE id = :id AND company_id = :companyId AND status = :status",
            nativeQuery = true)
    Optional<Vehicle> findByIdAndCompanyIdAndStatusAvailable(
            @Param("id") Long id,
            @Param("companyId") Long companyId,
            @Param("status") String AVAILABLE);

    @Query(value = "SElECT * FROM Vehicle WHERE company_id = :companyId AND company_district_group_id = :districtGroupId AND status = :status",
            nativeQuery = true)
    Optional<List<Vehicle>> findAllByCompanyIdAndCompanyDistrictGroupIdAndStatusAvailable(
            @Param("companyId") Long companyId,
            @Param("districtGroupId") Long districtGroupId,
            @Param("status") String AVAILABLE);

    @Query(value = "SElECT * FROM Vehicle WHERE company_id = :companyId AND company_group_id = :companyGroupId AND status = :status",
            nativeQuery = true)
    Optional<List<Vehicle>> findAllByCompanyIdAndCompanyGroupIdAndStatusAvailable(
            @Param("companyId") Long companyId,
            @Param("companyGroupId") Long companyGroupId,
            @Param("status") String AVAILABLE);

    Optional<Vehicle> findByUserId(Long userId);

    Optional<List<Vehicle>> findAllByUserId(Long userId);

    Optional<List<Vehicle>> findAllByCompanyIdAndCompanyGroupId(Long companyId, Long companyGroupId);

    Optional<List<Vehicle>> findAllByCompanyIdAndCompanyFleetId(Long companyId, Long companyFleetId);

    @Query(value = "SElECT * FROM Vehicle WHERE id = :id AND company_id = :companyId AND company_fleet_id = :companyFleetId",
            nativeQuery = true)
    Optional<Vehicle> findByIdAndCompanyIdAndCompanyFleetId(
            @Param("id") Long id,
            @Param("companyId") Long companyId,
            @Param("companyFleetId") Long companyFleetId);

    @Query(value = "SElECT * FROM Vehicle WHERE company_district_group_id = :districtGroupId AND company_fleet_id = :companyFleetId AND company_id = :companyId",
            nativeQuery = true)
    Optional<List<Vehicle>> findAllByCompanyDistrictGroupIdAndCompanyFleetGroupIdAndCompanyId(
            @Param("districtGroupId") Long districtGroupId,
            @Param("companyFleetId") Long companyFleetId,
            @Param("companyId") Long companyId);

    @Query(value = "SElECT * FROM Vehicle WHERE company_group_id = :companyGroupId AND company_district_group_id = :districtGroupId" +
            " AND company_fleet_id = :companyFleetId AND company_id = :companyId", nativeQuery = true)
    Optional<List<Vehicle>> findByCompanyGroupIdAndCompanyDistrictGroupIdAndCompanyFleetIdAndCompanyId(
            @Param("companyGroupId") Long companyGroupId,
            @Param("districtGroupId") Long districtGroupId,
            @Param("companyFleetId") Long companyFleetId,
            @Param("companyId") Long companyId);


}
