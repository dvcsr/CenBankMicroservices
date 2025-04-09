package com.cenbank.compliance.controller;

import com.cenbank.compliance.constant.ComplianceConstants;
import com.cenbank.compliance.dto.*;
import com.cenbank.compliance.service.IDueDiligenceReportService;
import com.cenbank.compliance.service.IKYCService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Customer KYC Service REST API for Submit Report and get Report Details")
@RestController
@RequestMapping(path = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class ReportController {
    private IDueDiligenceReportService iDiligenceReportService;
    private IKYCService ikycService;

    @Operation(
            summary = "Create: new Due Diligence report",
            description = "an entry of a new report for a customer"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "HttpStatus.CREATED"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HttpStatus.INTERNAL_SERVER_ERROR",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> submitReport(@Valid @RequestBody AddReportDto addReportDto) {
        iDiligenceReportService.createReport(addReportDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(ComplianceConstants.STATUS_201, ComplianceConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Get all reports for specific customer"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HttpStatus.OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("")
    public ResponseEntity<List<GetReportDto>> fetchAllReports(@RequestParam
                                                           @Pattern(regexp = "[0-9]{1,10}", message = "error. must be using digit")
                                                           String customerId) {
        List<GetReportDto> list = iDiligenceReportService.getAllReports(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Operation(
            summary = "Update Report Progress",
            description = "Transactional"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HttpStatus.OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HttpStatus.EXPECTATION_FAILED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateReport(@Valid @RequestBody UpdateReportDto updateReportDto) {
        boolean isUpdated = iDiligenceReportService.updateProgress(updateReportDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(ComplianceConstants.STATUS_200, ComplianceConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ComplianceConstants.STATUS_417, ComplianceConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Create a KYC Summary"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "HttpStatus.CREATED"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HttpStatus.INTERNAL_SERVER_ERROR",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @PostMapping("/summary")
    public ResponseEntity<ResponseDto> createSummary(@Valid @RequestBody SummaryDto summaryDto) {
        ikycService.createSummary(summaryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(ComplianceConstants.STATUS_201, ComplianceConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Get specific customer's KYC Summary",
            description = "KYC Summary with period of validity based from all incoming reports"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "HttpStatus.CREATED"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HttpStatus.INTERNAL_SERVER_ERROR",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/summary")
    public ResponseEntity<GetSummaryDto> getSummary(@RequestParam @Valid String customerId) {
        GetSummaryDto summary = ikycService.getSummary(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(summary);
    }

    @Operation(
            summary = "Update Summary Status",
            description = "Transactional"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HttpStatus.OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HttpStatus.EXPECTATION_FAILED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/summary/update")
    public ResponseEntity<ResponseDto> updateSummary(@Valid @RequestBody SummaryDto summaryDto) {
        boolean isUpdated = ikycService.updateSummary(summaryDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(ComplianceConstants.STATUS_200, ComplianceConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ComplianceConstants.STATUS_417, ComplianceConstants.MESSAGE_417_UPDATE));
        }
    }
}
