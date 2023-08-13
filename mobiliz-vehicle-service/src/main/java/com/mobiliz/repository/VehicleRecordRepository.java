package com.mobiliz.repository;

import com.mobiliz.model.Vehicle;
import com.mobiliz.model.VehicleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRecordRepository extends JpaRepository<VehicleRecord, Long> {

   Optional< VehicleRecord> findByVehicleId(Long id);

}
