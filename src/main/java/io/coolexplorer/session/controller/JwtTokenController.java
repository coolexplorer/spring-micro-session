package io.coolexplorer.session.controller;

import io.coolexplorer.session.dto.JwtTokenDTO;
import io.coolexplorer.session.model.JwtToken;
import io.coolexplorer.session.service.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class JwtTokenController {
    private final JwtTokenService jwtTokenService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Token Cache Creation", description = "Token Cache Creation", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = JwtTokenDTO.JwtTokenInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(example = "")))
    })
    @PostMapping("/token")
    public JwtTokenDTO.JwtTokenInfo createToken(@RequestBody JwtTokenDTO.JwtTokenCreateRequest request) {
        JwtToken jwtToken = modelMapper.map(request, JwtToken.class);
        JwtToken createdToken = jwtTokenService.create(jwtToken);

        return JwtTokenDTO.JwtTokenInfo.from(createdToken, modelMapper);
    }

    @Operation(summary = "Token Cache Update", description = "Token Cache Update", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = JwtTokenDTO.JwtTokenInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(example = "")))
    })
    @PutMapping("/token")
    public JwtTokenDTO.JwtTokenInfo updateToken(@RequestBody JwtTokenDTO.JwtTokenCreateRequest request) {
        JwtToken jwtToken = modelMapper.map(request, JwtToken.class);
        JwtToken createdToken = jwtTokenService.update(jwtToken);

        return JwtTokenDTO.JwtTokenInfo.from(createdToken, modelMapper);
    }

    @Operation(summary = "Token Cache Retrieve by Id", description = "Token Cache Retrieve by Id", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = JwtTokenDTO.JwtTokenInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(example = "")))
    })
    @GetMapping("/token/{id}")
    public JwtTokenDTO.JwtTokenInfo getToken(@PathVariable("id") String id) {
        JwtToken jwtToken = jwtTokenService.getToken(id);

        return JwtTokenDTO.JwtTokenInfo.from(jwtToken, modelMapper);
    }

    @Operation(summary = "Token Cache Retrieve by Params", description = "Token Cache Retrieve by Params", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = JwtTokenDTO.JwtTokenInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(example = "")))
    })
    @GetMapping("/token")
    public JwtTokenDTO.JwtTokenInfo getToken(JwtTokenDTO.JwtTokenParams params) {
        JwtToken jwtToken = jwtTokenService.getTokenByAccountId(params.getAccountId());

        return JwtTokenDTO.JwtTokenInfo.from(jwtToken, modelMapper);
    }
}
