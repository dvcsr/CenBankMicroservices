package com.cenbank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Submit Form for Full Customer Report",
        description = "required minimal information to get Full Customer Report"
)
@Data
public class GetFullCustomerReportInputDto {

    @Schema(
            description = "name of customer",
            example = "John Martinez"
    )
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Schema(
            description = "email of customer",
            example = "something@domain.com"
    )
    @Email(message = "Email should be in email format (ie: myemail@domain.com)")
    @NotBlank(message = "email cannot be empty")
    private String email;

    @Schema(
            description = "phone of customer. one customer only can submit one phone number",
            example = "00009999"
    )
    @NotBlank(message = "phone cannot be empty")
    @Pattern(regexp = "[0-9]{8,15}", message = "phone must be using number digits with normal length")
    private String phoneNumber;
    @Schema(
            description = "one valid account number that owned by the customer"
    )
    @Pattern(regexp = "[0-9]{10}", message = "account number cannot be empty and must be valid")
    private Long accountNumber;
}
