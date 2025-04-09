package com.cenbank.compliance.repository;

import com.cenbank.compliance.model.DueDiligenceReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DueDiligenceReportRepository extends JpaRepository<DueDiligenceReport, Long> {

    Optional<DueDiligenceReport> findByReportId(Long reportId);
    List<DueDiligenceReport> getAllByCustomerId(Long customerId);

}
