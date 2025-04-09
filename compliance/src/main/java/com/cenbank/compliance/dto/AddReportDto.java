package com.cenbank.compliance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(
        name = "Add Report",
        description = "Schema for add new report detail for specific customer"
)
@Data
public class AddReportDto {

    @NotBlank(message = "customer Id cannot be empty")
    private String customerId;
    @NotBlank(message = "report content cannot be empty")
    private String reportBody;
}
