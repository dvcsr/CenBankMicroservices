package com.cenbank.account.service.client;

import com.cenbank.account.dto.GetReportDto;
import com.cenbank.account.dto.GetSummaryDto;
import com.cenbank.account.dto.ResponseDto;
import com.cenbank.account.dto.SummaryDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "COMPLIANCE")
public interface ComplianceFeignClient {

    //no consume no produce yet
    @PostMapping(value = "/api/report/summary")
    public ResponseEntity<ResponseDto> createSummary(@RequestBody SummaryDto summaryDto);

    @GetMapping("/api/report/summary")
    public ResponseEntity<GetSummaryDto> getSummaryDto(@RequestParam String customerId);

    @GetMapping("api/report")
    public ResponseEntity<List<GetReportDto>> fetchAllReports(@RequestParam String customerId);
}
