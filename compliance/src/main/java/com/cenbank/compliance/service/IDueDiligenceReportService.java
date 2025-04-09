package com.cenbank.compliance.service;

import com.cenbank.compliance.dto.GetReportDto;
import com.cenbank.compliance.dto.AddReportDto;
import com.cenbank.compliance.dto.UpdateReportDto;

import java.util.List;

public interface IDueDiligenceReportService {

    void createReport(AddReportDto addReportDto);
    boolean updateProgress(UpdateReportDto updateReportDto);
    List<GetReportDto> getAllReports(String customerId);
}
