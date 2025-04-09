package com.cenbank.compliance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Response",
        description = "Http status of response"
)
@Data @AllArgsConstructor
public class ResponseDto {
    @Schema(
            description = "Http status of response"

    )
    private String statusCode;
    @Schema(
            description = "detail of message"
    )
    private String statusMessage;
}
