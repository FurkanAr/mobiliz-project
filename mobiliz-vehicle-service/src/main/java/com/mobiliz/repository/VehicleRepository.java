package com.mobiliz.repository;

import com.mobiliz.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByCompanyGroupId(Long id);

    List<Vehicle> findAllByCompanyDistrictGroupId(Long id);
}
