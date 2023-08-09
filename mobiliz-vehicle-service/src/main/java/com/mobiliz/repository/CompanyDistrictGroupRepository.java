package com.mobiliz.repository;

import com.mobiliz.model.CompanyDistrictGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDistrictGroupRepository extends JpaRepository<CompanyDistrictGroup, Long> {
    List<CompanyDistrictGroup> findAllByCompanyFleetGroupId(Long id);
}
