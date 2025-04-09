package com.cenbank.compliance.service.impl;

import com.cenbank.compliance.dto.GetReportDto;
import com.cenbank.compliance.dto.AddReportDto;
import com.cenbank.compliance.dto.UpdateReportDto;
import com.cenbank.compliance.exception.ResourceNotFoundException;
import com.cenbank.compliance.mapper.ComplianceMapper;
import com.cenbank.compliance.model.DueDiligenceReport;
import com.cenbank.compliance.repository.DueDiligenceReportRepository;
import com.cenbank.compliance.service.IDueDiligenceReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DueDiligenceReportServiceImpl implements IDueDiligenceReportService {
    private DueDiligenceReportRepository dueDiligenceReportRepository;

    @Override
    public void createReport(AddReportDto addReportDto) {
        DueDiligenceReport report = ComplianceMapper.mapNewReportDtoToDueDiligenceReport(addReportDto, new DueDiligenceReport());
        report.setReportId(generateReportId());
        dueDiligenceReportRepository.save(report);
    }

    @Override
    public boolean updateProgress(UpdateReportDto updateReportDto) {
        boolean isUpdated = false;
        DueDiligenceReport report = dueDiligenceReportRepository.findByReportId(Long.parseLong(updateReportDto.getReportId())).orElseThrow(
                () -> new ResourceNotFoundException("Report", "Report Id:", updateReportDto.getReportId().toString())
        );
        report.setProgress(updateReportDto.getProgress());
        dueDiligenceReportRepository.save(report);
        isUpdated = true;
        return  isUpdated;
    }

    @Override
    public List<GetReportDto> getAllReports(String customerId) {
        List<DueDiligenceReport> reportList = dueDiligenceReportRepository.getAllByCustomerId(Long.parseLong(customerId));
        if (reportList.isEmpty()) {
            throw new ResourceNotFoundException("Report List", "customer id: ", customerId);
        }
        List<GetReportDto> reportDtoList = reportList.stream().map(
                report -> GetReportDto.builder()
                        .reportId(report.getReportId().toString())
                        .customerId(report.getCustomerId().toString())
                        .reportBody(report.getReportBody())
                        .progress(report.getProgress())
                        .build()).collect(Collectors.toList());
        return reportDtoList;
    }

    private Long generateReportId() {
        // Get current year and month
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear() % 100;  // Get last 2 digits of year
        int month = now.getMonthValue(); // Get month (1-12)

        // Generate random number between 0-9999
        int randomNum = (int) (Math.random() * 10000);

        String ans = String.format("%02d%02d%04d", year, month, randomNum);
        return Long.parseLong(ans);

    }
}

