package com.cenbank.compliance.mapper;

import com.cenbank.compliance.constant.ComplianceConstants;
import com.cenbank.compliance.dto.AddReportDto;
import com.cenbank.compliance.dto.GetSummaryDto;
import com.cenbank.compliance.dto.SummaryDto;
import com.cenbank.compliance.model.DueDiligenceReport;
import com.cenbank.compliance.model.KYCSummary;

public class ComplianceMapper {

    public static DueDiligenceReport mapNewReportDtoToDueDiligenceReport(AddReportDto Dto, DueDiligenceReport report) {
        report.setReportBody(Dto.getReportBody());
        report.setCustomerId(Long.parseLong(Dto.getCustomerId()));
        report.setProgress(ComplianceConstants.DD_PROGRESS_NOT_STARTED);
        return report;
    }

    public static KYCSummary mapSubmitSummaryDtoToKYCSummary(SummaryDto Dto, KYCSummary summary) {
        summary.setCustomerId(Long.parseLong(Dto.getCustomerId()));
        summary.setKycStatus(Dto.getKycStatus());
        return summary;
    }

    public static GetSummaryDto mapToGetSummaryDto(KYCSummary summary, GetSummaryDto dto) {
        dto.setCustomerId(summary.getCustomerId().toString());
        dto.setKycStatus(summary.getKycStatus());
        return dto;
    }

}
