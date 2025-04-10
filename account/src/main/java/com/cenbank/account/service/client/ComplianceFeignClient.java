package com.cenbank.account.service.client;

import com.cenbank.account.dto.ResponseDto;
import com.cenbank.account.dto.SummaryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("compliance")
public interface ComplianceFeignClient {

    //no consume no produce yet
    @PostMapping(value = "/api/report/summary")
    public ResponseEntity<ResponseDto> createSummary(@RequestBody SummaryDto summaryDto);
}
