package com.cenbank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Summary DTO",
        description = "Schema for add/update Customer's KYC status"
)
@Data
public class SummaryDto {

    @NotBlank(message = "customer Id cannot be empty")
    private String customerId;

    @Schema(
            description = "value for KYC Status: CLEAN, AT RISK",
            example = "AT RISK"
    )
    @Pattern(regexp = "^CLEAN$|^AT RISK$|^EXPIRED$",
            message = "value options: CLEAN, AT RISK")
    private String kycStatus;
}
