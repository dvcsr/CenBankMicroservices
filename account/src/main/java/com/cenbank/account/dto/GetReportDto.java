package com.cenbank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(
        name = "Get Report",
        description = "Schema to fetch specific report detail"
)
@Data
@Builder
public class GetReportDto {
    @Schema(
            description = "report id"
    )
    private String reportId;
    @Schema(
            description = "customer id"
    )
    private String customerId;
    @Schema(
            description = "content of report",
            example = "customer is a Politically Exposed Person(PEP)"
    )
    private String reportBody;
    @Schema(
            description = "progress status",
            example = "SUSPICIOUS"
    )
    private String progress;
}
