package com.mobiliz.repository;

import com.mobiliz.model.CompanyFleetGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyFleetGroupRepository extends JpaRepository<CompanyFleetGroup, Long> {
    Optional<List<CompanyFleetGroup>> findAllByCompanyId(Long id);

    @Query(value = "SElECT * FROM Company_Fleet_Group WHERE company_id = :companyId AND name = :name ", nativeQuery = true)
    Optional<CompanyFleetGroup> findByNameAndCompanyId(@Param("companyId") Long companyId,
                                                       @Param("name") String name);
}
