package com.mobiliz.repository;

import com.mobiliz.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByAdminId(Long id);
    Optional<Company> findByName(String name);
}
