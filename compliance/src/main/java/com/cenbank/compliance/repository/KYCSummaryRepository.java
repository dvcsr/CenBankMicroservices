package com.cenbank.compliance.repository;

import com.cenbank.compliance.model.DueDiligenceReport;
import com.cenbank.compliance.model.KYCSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KYCSummaryRepository extends JpaRepository<KYCSummary, Long> {
    Optional<KYCSummary> findByCustomerId(Long customerId);
    boolean existsByCustomerId(Long customerId);
}
