package com.mobiliz.repository;

import com.mobiliz.model.CompanyGroup;
import com.mobiliz.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByCompanyGroupId(Long id);

    List<Vehicle> findAllByCompanyDistrictGroupId(Long id);

    Optional<List<Vehicle>> findAllByCompanyId(Long id);

    @Query(value = "SElECT * FROM Vehicle WHERE id = :id AND company_id = :companyId", nativeQuery = true)
    Vehicle findByIdAndCompanyId(@Param("id") Long id,
                                                  @Param("companyId") Long companyId);

}
