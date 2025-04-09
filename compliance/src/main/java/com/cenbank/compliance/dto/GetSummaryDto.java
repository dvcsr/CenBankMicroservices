package com.cenbank.compliance.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.beans.Transient;
import java.time.LocalDateTime;

@Schema(
        name = "Get Summary",
        description = "Schema to fetch Customer's KYC Summary"
)
@Data
public class GetSummaryDto {

    private String customerId;

    @Schema(
            description = "value for KYC Status: CLEAN, AT RISK",
            example = "AT RISK"
    )
    private String kycStatus;

    @Schema(
            description = "validity based on valid duration"
    )
    private String validity;

    @Schema(
            description = "date when the summary status expired"
    )
    private String ExpiredAt;

    @Schema(
            description = "number of total report history for the customer"
    )
    private Integer reportCount;
}
