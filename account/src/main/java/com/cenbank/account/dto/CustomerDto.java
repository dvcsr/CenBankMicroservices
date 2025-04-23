package com.cenbank.account.dto;

import com.cenbank.account.validation.ValidPhoneCountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.Data;
import java.util.List;

@Schema(
        name = "Customer",
        description = "Schema for Customer information"
)
@Data
@ValidPhoneCountryCode(message = "Phone number doesn't match the country code")
public class CustomerDto {

    @Schema(
            description = "name of customer",
            example = "John Martinez"
    )
    @NotBlank(message = "name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Min(value = 18, message = "age must be at least 18")
    @Max(value = 130, message = "age must be at most 130")
    private Integer age;

    @Schema(
            description = "email of customer",
            example = "something@domain.com"
    )
    @Email(message = "Email should be in email format (ie: myemail@domain.com)")
    @NotBlank(message = "email cannot be empty")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    private String email;

    @Schema(
            description = "Phone customer with country code",
            example = "12349999"
    )
    @NotBlank(message = "phone cannot be empty")
    @Pattern(regexp = "^(?!0000)[0-9]{8,15}$", message = "phone must be using number digits and cannot start with 0000")
    private String phoneNumber;
    private List<AccountsDto> accountsDtoList;
    @NotBlank(message = "country cannot be empty!")
    @Pattern(regexp = "^(US|UK|ID)$", message = "Country must be a valid country code!")
    private String countryId;
}

