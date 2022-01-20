package io.coolexplorer.session.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.coolexplorer.session.dto.SessionDTO;
import io.coolexplorer.session.model.Session;
import io.coolexplorer.session.service.SessionService;
import io.coolexplorer.test.builder.TestSessionBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import javax.validation.ConstraintViolation;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yaml")
public class SessionControllerWebMvcTest extends SpringBootWebMvcTestSupport {
    @MockBean
    private SessionService sessionService;

    Session dtoSession;
    Session defaultSession;

    @BeforeEach
    void setUp() throws Exception {
        super.setUp();

        dtoSession = TestSessionBuilder.defaultSession();
        defaultSession = TestSessionBuilder.defaultSession(TestSessionBuilder.ID);
    }

    @Nested
    @DisplayName("Session Creation Test")
    class SessionCreationTest {
        @Test
        @DisplayName("Success")
        void testCreateSession() throws Exception {
            SessionDTO.SessionCreateRequest request = createValidSessionRequest();
            SessionDTO.SessionInfo sessionInfo = SessionDTO.SessionInfo.from(defaultSession, modelMapper);

            String payload = objectMapper.writeValueAsString(request);
            String expectedResponse = objectMapper.writeValueAsString(sessionInfo);

            when(sessionService.create(any())).thenReturn(defaultSession);

            mockMvc.perform(post("/api/v1/session")
                    .content(payload)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andExpect(content().json(expectedResponse));
        }
    }

    private SessionDTO.SessionCreateRequest createValidSessionRequest() {
        return modelMapper.map(defaultSession, SessionDTO.SessionCreateRequest.class);
    }

    @Nested
    @DisplayName("Session Creation Validation Test")
    class SessionCreationValidationTest {
        @Test
        @DisplayName("Success")
        void testSessionCreationValidation() {
            SessionDTO.SessionCreateRequest request = createValidSessionRequest();

            Set<ConstraintViolation<SessionDTO.SessionCreateRequest>> violations = validator.validate(request);

            assertThat(violations.size()).isZero();
        }

        @Test
        @DisplayName("Failed with empty request")
        void testSessionCreationValidationWithEmptyRequest() {
            SessionDTO.SessionCreateRequest request = new SessionDTO.SessionCreateRequest();

            Set<ConstraintViolation<SessionDTO.SessionCreateRequest>> violations = validator.validate(request);
            Map<String, ConstraintViolation<SessionDTO.SessionCreateRequest>> violationMap = convertMap(violations);

            ConstraintViolation accountIdViolation = violationMap.get("accountId");
            ConstraintViolation valuesViolation = violationMap.get("values");

            assertThat(violations.size()).isEqualTo(2);
            assertThat(accountIdViolation.getMessage()).isEqualTo(getValidationMessage("account.id.empty"));
            assertThat(valuesViolation.getMessage()).isEqualTo(getValidationMessage("session.value.empty"));
        }

        @Test
        @DisplayName("Failed with wrong json format")
        void testSessionCreationValidationWithWrongJsonFormat() {
            SessionDTO.SessionCreateRequest request = createValidSessionRequest();
            request.setValues("wrong json format");

            Set<ConstraintViolation<SessionDTO.SessionCreateRequest>> violations = validator.validate(request);
            Map<String, ConstraintViolation<SessionDTO.SessionCreateRequest>> violationMap = convertMap(violations);

            ConstraintViolation valuesViolation = violationMap.get("values");

            assertThat(violations.size()).isEqualTo(1);
            assertThat(valuesViolation.getMessage()).isEqualTo(getValidationMessage("session.value.not.json"));
        }
    }

    @Nested
    @DisplayName("Session Update Test")
    class SessionUpdateTest {
        @Test
        @DisplayName("Success")
        void testUpdateSession() throws Exception {
            SessionDTO.SessionCreateRequest request = createValidSessionRequest();

            SessionDTO.SessionInfo sessionInfo = SessionDTO.SessionInfo.from(defaultSession, modelMapper);

            String payload = objectMapper.writeValueAsString(request);
            String expectedResponse = objectMapper.writeValueAsString(sessionInfo);

            when(sessionService.create(any())).thenReturn(defaultSession);

            mockMvc.perform(put("/api/v1/session/" + TestSessionBuilder.ID)
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andExpect(content().json(expectedResponse));
        }
    }

    @Nested
    @DisplayName("Session Retrieve Test")
    class SessionRetrieveTest {
        @Test
        @DisplayName("Success")
        void testRetrieveSession() throws Exception {
            SessionDTO.SessionInfo sessionInfo = SessionDTO.SessionInfo.from(defaultSession, modelMapper);

            when(sessionService.getSession(anyString())).thenReturn(defaultSession);

            String expectedResponse = objectMapper.writeValueAsString(sessionInfo);

            mockMvc.perform(get("/api/v1/session/" + TestSessionBuilder.ID))
                    .andDo(print())
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andExpect(content().json(expectedResponse));
        }

        @Test
        @DisplayName("Success with Account Id")
        void testRetrieveSessionWithAccountId() throws Exception {
            SessionDTO.SessionInfo sessionInfo = SessionDTO.SessionInfo.from(defaultSession, modelMapper);

            when(sessionService.getSession(anyLong())).thenReturn(defaultSession);

            String expectedResponse = objectMapper.writeValueAsString(sessionInfo);

            mockMvc.perform(get("/api/v1/session")
                        .queryParam("accountId", "1"))
                    .andDo(print())
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andExpect(content().json(expectedResponse));
        }
    }

    @Nested
    @DisplayName("Session Deletion Test")
    class SessionDeletionTest {
        @Test
        @DisplayName("Success")
        void testDeleteSession() throws Exception {
            mockMvc.perform(delete("/api/v1/session/" + TestSessionBuilder.ID))
                    .andDo(print())
                    .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

            verify(sessionService).delete(TestSessionBuilder.ID);
        }
    }
}
