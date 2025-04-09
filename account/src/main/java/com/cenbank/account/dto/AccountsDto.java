package com.cenbank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Schema(
        name = "Account",
        description = "Schema for Account detail owned by existing Customer"
)
@Data
@Builder
public class AccountsDto {
    @NotBlank(message = "account number cannot be empty")
    @Pattern(regexp = "[0-9]{10}")
    private Long accountNumber;
    @NotBlank(message = "branch cannot be empty")
    private String accountType;
    @NotBlank(message = "branch cannot be empty")
    private String branchAddress;
}

