package com.cenbank.compliance.controller;

import com.cenbank.compliance.dto.ErrorResponseDto;
import com.cenbank.compliance.dto.HelpContactDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Help: for more information behind the application")
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class HelpController {
    private final HelpContactDto helpContactDto;

    public HelpController(HelpContactDto helpContactDto) {
        this.helpContactDto = helpContactDto;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Operation(
            summary = "get more information on help about the application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HttpStatus.OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/help")
    public ResponseEntity<HelpContactDto> getHelp() {
        return ResponseEntity.status(HttpStatus.OK).body(helpContactDto);
    }

    @Operation(
            summary = "get build information",
            description = "get build information about the app"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/build")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }
}
