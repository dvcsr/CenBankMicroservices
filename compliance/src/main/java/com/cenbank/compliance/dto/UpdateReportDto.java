package com.cenbank.compliance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Update Report",
        description = "Schema to update report progress"
)
@Data
public class UpdateReportDto {

    @NotBlank(message = "report Id cannot be empty")
    private String reportId;

    @Schema(
            description = "value for progress: NOT STARTED, INVESTIGATING, NO ISSUE, SUSPICIOUS",
            example = "NO ISSUE"
    )
    @NotBlank(message = "progress cannot be empty")
    @Pattern(regexp = "^NOT STARTED$|^INVESTIGATING$|^NO ISSUE$|^SUSPICIOUS$",
            message = "value options: NOT STARTED, INVESTIGATING, NO ISSUE, SUSPICIOUS")
    private String progress;

}
