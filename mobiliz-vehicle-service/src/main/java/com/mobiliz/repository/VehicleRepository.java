package com.mobiliz.repository;

import com.mobiliz.model.Vehicle;
import com.mobiliz.model.enums.VehicleStatus;
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

}
