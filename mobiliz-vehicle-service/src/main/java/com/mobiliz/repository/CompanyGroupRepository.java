package com.mobiliz.repository;

import com.mobiliz.model.CompanyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyGroupRepository extends JpaRepository<CompanyGroup, Long> {
    List<CompanyGroup> findAllByCompanyDistrictGroupId(Long id);
}
