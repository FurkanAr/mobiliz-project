package com.mobiliz.repository;

import com.mobiliz.model.CompanyDistrictGroup;
import com.mobiliz.model.CompanyFleetGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyDistrictGroupRepository extends JpaRepository<CompanyDistrictGroup, Long> {
    List<CompanyDistrictGroup> findAllByCompanyFleetGroupId(Long id);
    Optional<List<CompanyDistrictGroup>> findAllByCompanyId(Long id);

    @Query(value = "SElECT * FROM CompanyDistrictGroup WHERE company_id = :companyId AND name = :name ", nativeQuery = true)
    Optional<CompanyDistrictGroup> findByNameAndCompanyId(@Param("companyId") Long companyId,
                                                          @Param("name") String name);
}
