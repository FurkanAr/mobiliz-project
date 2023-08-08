package com.mobiliz.repository;

import com.mobiliz.model.CompanyFleetGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyFleetGroupRepository extends JpaRepository<CompanyFleetGroup, Long> {
}
