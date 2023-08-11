package com.mobiliz.repository;

import com.mobiliz.model.CompanyFleetGroup;
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

}
