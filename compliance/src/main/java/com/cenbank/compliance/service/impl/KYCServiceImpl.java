package com.cenbank.compliance.service.impl;

import com.cenbank.compliance.constant.ComplianceConstants;
import com.cenbank.compliance.dto.GetSummaryDto;
import com.cenbank.compliance.dto.SummaryDto;
import com.cenbank.compliance.exception.ResourceNotFoundException;
import com.cenbank.compliance.mapper.ComplianceMapper;
import com.cenbank.compliance.model.KYCSummary;
import com.cenbank.compliance.repository.DueDiligenceReportRepository;
import com.cenbank.compliance.repository.KYCSummaryRepository;
import com.cenbank.compliance.service.IKYCService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class KYCServiceImpl implements IKYCService {
    private KYCSummaryRepository kycSummaryRepository;
    private DueDiligenceReportRepository dueDiligenceReportRepository;

    @Override
    public void createSummary(SummaryDto summaryDto) {
        KYCSummary summary = ComplianceMapper.mapSubmitSummaryDtoToKYCSummary(summaryDto, new KYCSummary());
        System.out.println("dto: " + summaryDto.getKycStatus());
        System.out.println("entity: " + summary.getKycStatus());
        KYCSummary savedSummary = kycSummaryRepository.save(summary);
        savedSummary.setExpiredDate(savedSummary.getCreatedAt().plus(ComplianceConstants.KYC_VALIDITY_DURATION));
        kycSummaryRepository.save(savedSummary);
    }

    @Override
    public GetSummaryDto getSummary(String customerId) {
        KYCSummary summary = kycSummaryRepository.findByCustomerId(Long.parseLong(customerId)).orElseThrow(
                () -> new ResourceNotFoundException("KYC Status", "customerId", customerId)
        );
        GetSummaryDto dto = ComplianceMapper.mapToGetSummaryDto(summary, new GetSummaryDto());
        if (LocalDateTime.now().isAfter(summary.getExpiredDate())) {
            dto.setValidity(ComplianceConstants.KYC_STATUS_EXPIRED);
            dto.setExpiredAt(summary.getExpiredDate().toString());
        }
        else {
            dto.setValidity("ACTIVE");
            dto.setExpiredAt(summary.getExpiredDate().toString());
        }
        Integer cases = dueDiligenceReportRepository.getAllByCustomerId(Long.parseLong(customerId)).size();
        dto.setReportCount(cases);

        return dto;
    }

    @Override
    public boolean updateSummary(SummaryDto updateSummaryDto) {
        boolean isUpdated = false;
        KYCSummary summary = kycSummaryRepository.findByCustomerId(Long.parseLong(updateSummaryDto.getCustomerId())).orElseThrow(
                () -> new ResourceNotFoundException("Summary", "customerId:", updateSummaryDto.getCustomerId().toString())
        );
        summary = ComplianceMapper.mapSubmitSummaryDtoToKYCSummary(updateSummaryDto, summary);
        KYCSummary savedSummary = kycSummaryRepository.save(summary);
        savedSummary.setExpiredDate(savedSummary.getUpdatedAt().plus(ComplianceConstants.KYC_VALIDITY_DURATION));
        kycSummaryRepository.save(savedSummary);
        isUpdated = true;
        return isUpdated;
    }
}
