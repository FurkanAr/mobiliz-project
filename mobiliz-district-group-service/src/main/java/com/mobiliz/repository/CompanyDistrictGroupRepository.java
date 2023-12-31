package com.mobiliz.repository;

import com.mobiliz.model.CompanyDistrictGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyDistrictGroupRepository extends JpaRepository<CompanyDistrictGroup, Long> {

    @Query(value = "SElECT * FROM Company_District_Group WHERE company_id = :companyId AND name = :name ", nativeQuery = true)
    Optional<CompanyDistrictGroup> findByNameAndCompanyId(@Param("companyId") Long companyId,
                                                          @Param("name") String name);

    @Query(value = "SElECT * FROM Company_District_Group WHERE company_id = :companyId AND company_fleet_id = :fleetId ", nativeQuery = true)
    Optional<List<CompanyDistrictGroup>> findAllByCompanyIdAndCompanyFleetId(@Param("companyId") Long companyId,
                                                                             @Param("fleetId") Long fleetId);


    @Query(value = "SElECT * FROM Company_District_Group WHERE id = :id AND company_id = :companyId AND company_fleet_id = :fleetId ", nativeQuery = true)
    Optional<CompanyDistrictGroup> findByIdAndCompanyIdAndCompanyFleetId(
            @Param("id") Long id, @Param("companyId") Long companyId,
            @Param("fleetId") Long fleetId);

    @Query(value = "SElECT * FROM Company_District_Group WHERE id = :id", nativeQuery = true)
    Optional<List<CompanyDistrictGroup>> findAllById(@Param("id") Long id);

    @Query(value = "SElECT * FROM Company_District_Group WHERE id = :id AND company_fleet_id = :fleetId ", nativeQuery = true)
    Optional<CompanyDistrictGroup> findAllByIdAndCompanyFleetId(@Param("id") Long id,
                                                                             @Param("fleetId") Long fleetId);

    @Query(value = "SElECT * FROM Company_District_Group WHERE company_fleet_id = :companyFleetId AND name = :name ", nativeQuery = true)
    Optional<CompanyDistrictGroup> findByNameAndCompanyFleetId(@Param("companyFleetId") Long companyFleetId,
                                                          @Param("name") String name);

}