package com.cenbank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(
        name = "Full Customer Report",
        description = "Personal Information + Account Information + KYC report information of a customer"
)
@Data
public class GetFullCustomerReportDto {
    private String name;
    private String email;
    private String phoneNumber;
    private List<AccountsDto> accountsDtoList;
    private GetSummaryDto getSummaryDto;
    private List<GetReportDto> reportDtoList;
}
