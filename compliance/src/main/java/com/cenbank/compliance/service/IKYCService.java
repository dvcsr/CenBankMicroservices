package com.cenbank.compliance.service;

import com.cenbank.compliance.dto.GetSummaryDto;
import com.cenbank.compliance.dto.SummaryDto;

public interface IKYCService {

    void createSummary(SummaryDto getSummaryDto);
    GetSummaryDto getSummary(String customerId);
    boolean updateSummary(SummaryDto updateSummaryDto);
}
