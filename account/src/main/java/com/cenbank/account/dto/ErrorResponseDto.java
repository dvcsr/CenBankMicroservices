package com.cenbank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "Error Response",
        description = "Schema for any error response"
)
@Data @AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "detail of API path that being invoked in client app",
            example = "api/create/example"
    )
    private String apiPath;
    @Schema(
            description = "error code representing what happened",
            example = "500"
    )
    private HttpStatus errorCode;
    @Schema(
            description = "error message representing what happened",
            example = "failed. server is busy"
    )
    private String errorMessage;
    @Schema(
            description = "time when error happened",
            example = "11.30.33"
    )
    private LocalDateTime errorTime;

}
