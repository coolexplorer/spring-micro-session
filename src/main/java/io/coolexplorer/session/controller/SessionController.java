package io.coolexplorer.session.controller;

import io.coolexplorer.session.dto.JwtTokenDTO;
import io.coolexplorer.session.dto.SessionDTO;
import io.coolexplorer.session.model.Session;
import io.coolexplorer.session.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SessionController {
    private final SessionService sessionService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Session Creation", description = "Session Creation", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SessionDTO.SessionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(example = "")))
    })
    @PostMapping("/session")
    public SessionDTO.SessionInfo createSession(@RequestBody SessionDTO.SessionCreateRequest request) {
        Session session = modelMapper.map(request, Session.class);
        Session createdSession = sessionService.create(session);

        return SessionDTO.SessionInfo.from(createdSession, modelMapper);
    }

    @Operation(summary = "Session Update", description = "Session Update", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SessionDTO.SessionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(example = "")))
    })
    @PutMapping("/session/{sessionId}")
    public SessionDTO.SessionInfo updateSession(@PathVariable("sessionId") String id, @RequestBody SessionDTO.SessionCreateRequest request) {
        Session session = modelMapper.map(request, Session.class);
        Session updatedSession = sessionService.create(session.setId(id));

        return SessionDTO.SessionInfo.from(updatedSession, modelMapper);
    }

    @Operation(summary = "Session Retrieve", description = "Session Retrieve", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SessionDTO.SessionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(example = "")))
    })
    @GetMapping("/session/{sessionId}")
    public SessionDTO.SessionInfo getSessionById(@PathVariable("sessionId") String id) {
        Session session = sessionService.getSession(id);

        return SessionDTO.SessionInfo.from(session, modelMapper);
    }

    @Operation(summary = "Session Retrieve", description = "Session Retrieve", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SessionDTO.SessionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(example = "")))
    })
    @GetMapping("/session")
    public SessionDTO.SessionInfo getSession(SessionDTO.SessionSearchParams params) {
        Session session = sessionService.getSession(params.getAccountId());

        return SessionDTO.SessionInfo.from(session, modelMapper);
    }

    @Operation(summary = "Session Deletion", description = "Session Deletion", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SessionDTO.SessionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(example = "")))
    })
    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable("sessionId") String id) {
        sessionService.delete(id);

        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
