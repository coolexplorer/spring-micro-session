package io.coolexplorer.session.service.impl;

import io.coolexplorer.session.model.Session;
import io.coolexplorer.session.repository.SessionRepository;
import io.coolexplorer.session.service.SessionService;
import io.coolexplorer.test.builder.TestSessionBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Slf4j
@ExtendWith(MockitoExtension.class)
public class SessionServiceImplTest {
    @Mock
    private SessionRepository sessionRepository;

    private SessionService sessionService;

    Session dtoSession;
    Session defaultSession;

    @BeforeEach
    void setUp() {
        sessionService = new SessionServiceImpl(
                sessionRepository
        );

        dtoSession = TestSessionBuilder.defaultSession();
        defaultSession = TestSessionBuilder.defaultSession(TestSessionBuilder.ID);
    }

    @Nested
    @DisplayName("Session Creation Test")
    class SessionCreationTest {
        @Test
        @DisplayName("Success")
        void testCreateSession() {
            when(sessionRepository.save(any())).thenReturn(defaultSession);

            Session createdSession = sessionService.create(dtoSession);

            assertThat(createdSession).isNotNull().isEqualTo(defaultSession);
        }
    }

    @Nested
    @DisplayName("Session Update Test")
    class SessionUpdateTest {
        @Test
        @DisplayName("Success")
        void testUpdateSession() {
            when(sessionRepository.findSessionByAccountId(anyLong())).thenReturn(defaultSession);
            when(sessionRepository.save(any())).thenReturn(defaultSession);

            Session updatedSession = sessionService.update(dtoSession);

            assertThat(updatedSession).isNotNull().isEqualTo(defaultSession);
        }
    }

    @Nested
    @DisplayName("Session Retrieve Test")
    class SessionRetrieveTest {
        @Test
        @DisplayName("Success by Id")
        void testRetrieveSession() {
            when(sessionRepository.findById(anyString())).thenReturn(Optional.of(defaultSession));

            Session retrievedSession = sessionService.getSession(TestSessionBuilder.ID);

            assertThat(retrievedSession).isNotNull().isEqualTo(retrievedSession);
        }

        @Test
        @DisplayName("Success by Account Id")
        void testRetrieveSessionByAccountId() {
            when(sessionRepository.findSessionByAccountId(anyLong())).thenReturn(defaultSession);

            Session retrievedSession = sessionService.getSession(TestSessionBuilder.ACCOUNT_ID);

            assertThat(retrievedSession).isNotNull().isEqualTo(retrievedSession);
        }
    }

    @Nested
    @DisplayName("Session Deletion Test")
    class SessionDeletionTest {
        @Test
        @DisplayName("Success")
        void testDeleteSession() {
            when(sessionRepository.findById(anyString())).thenReturn(Optional.of(defaultSession));

            sessionService.delete(TestSessionBuilder.ID);

            verify(sessionRepository).delete(defaultSession);
        }
    }
}
