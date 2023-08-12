package com.mobiliz.repository;

import com.mobiliz.model.VehicleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRecordRepository extends JpaRepository<VehicleRecord, Long> {
}
