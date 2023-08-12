package com.mobiliz.repository;

import com.mobiliz.model.CompanyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyGroupRepository extends JpaRepository<CompanyGroup, Long> {
    List<CompanyGroup> findAllByCompanyDistrictGroupId(Long id);

    Optional<List<CompanyGroup>> findAllByCompanyId(Long id);

    @Query(value = "SElECT * FROM Company_Group WHERE company_id = :companyId AND name = :name ", nativeQuery = true)
    Optional<CompanyGroup> findByNameAndCompanyId(@Param("companyId") Long companyId,
                                                  @Param("name") String name);

    @Query(value = "SElECT * FROM Company_Group WHERE company_id = :companyId AND company_fleet_id = :fleetId  AND company_district_group_id = :districtGroupId", nativeQuery = true)
    Optional<List<CompanyGroup>> findAllByCompanyIdAndCompanyFleetIdAndCompanyDistrictGroupId(
            @Param("companyId") Long companyId,
            @Param("fleetId") Long fleetId,
            @Param("districtGroupId") Long districtGroupId);
    @Query(value = "SElECT * FROM Company_Group WHERE id = :id AND  company_id = :companyId AND company_fleet_id = :fleetId  AND company_district_group_id = :districtGroupId", nativeQuery = true)
    Optional<CompanyGroup> findByIdAndCompanyIdAndCompanyFleetIdAndCompanyDistrictGroupId(
            @Param("id") Long id,
            @Param("companyId") Long companyId,
            @Param("fleetId") Long fleetId,
            @Param("districtGroupId") Long districtGroupId);
}
