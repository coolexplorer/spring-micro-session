package io.coolexplorer.session.service.impl;

import io.coolexplorer.session.model.JwtToken;
import io.coolexplorer.session.repository.JwtTokenRepository;
import io.coolexplorer.session.service.JwtTokenService;
import io.coolexplorer.test.builder.TestJwtTokenBuilder;
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
public class JwtTokenServiceImplTest {
    @Mock
    private JwtTokenRepository jwtTokenRepository;

    private JwtTokenService jwtTokenService;

    JwtToken dtoJwtToken;
    JwtToken defaultJwtToken;

    @BeforeEach
    void setUp() {
        jwtTokenService = new JwtTokenServiceImpl(
                jwtTokenRepository
        );

        dtoJwtToken = TestJwtTokenBuilder.defaultJwtToken();
        defaultJwtToken = TestJwtTokenBuilder.defaultJwtToken(TestJwtTokenBuilder.ID);
    }

    @Nested
    @DisplayName("JwtToken Cache Creation Test")
    class JwtTokenCacheCreationTest {
        @Test
        @DisplayName("Success")
        void testCreateJwtTokenCache() {
            when(jwtTokenRepository.save(any())).thenReturn(defaultJwtToken);

            JwtToken createdJwtToken = jwtTokenService.create(dtoJwtToken);

            assertThat(createdJwtToken).isNotNull().isEqualTo(defaultJwtToken);
        }
    }

    @Nested
    @DisplayName("JwtToken Cache Update Test")
    class JwtTokenCacheUpdateTest {
        @Test
        @DisplayName("Success")
        void testUpdateJwtTokenCache() {
            when(jwtTokenRepository.findJwtTokenByAccountId(anyLong())).thenReturn(defaultJwtToken);
            when(jwtTokenRepository.save(any())).thenReturn(defaultJwtToken);

            JwtToken updatedJwtToken = jwtTokenService.update(dtoJwtToken);

            assertThat(updatedJwtToken).isNotNull().isEqualTo(defaultJwtToken);
        }
    }

    @Nested
    @DisplayName("JwtToken Cache Retrieve Test")
    class JwtTokenCacheRetrieveTest {
        @Test
        @DisplayName("Success by Id")
        void testRetrieveJwtTokenCache() {
            when(jwtTokenRepository.findById(anyString())).thenReturn(Optional.of(defaultJwtToken));

            JwtToken retrievedToken = jwtTokenService.getToken(TestJwtTokenBuilder.ID);

            assertThat(retrievedToken).isNotNull().isEqualTo(defaultJwtToken);
        }

        @Test
        @DisplayName("Success by Account id")
        void testRetrieveJwtTokenCacheByAccountId() {
            when(jwtTokenRepository.findJwtTokenByAccountId(anyLong())).thenReturn(defaultJwtToken);

            JwtToken retrievedToken = jwtTokenService.getToken(TestJwtTokenBuilder.ACCOUNT_ID);

            assertThat(retrievedToken).isNotNull().isEqualTo(defaultJwtToken);
        }
    }

    @Nested
    @DisplayName("JwtToken Cache Deletion Test")
    class JwtTokenCacheDeletionTest {
        @Test
        @DisplayName("Success")
        void testDeleteJwtTokenCache() {
            when(jwtTokenRepository.findById(anyString())).thenReturn(Optional.of(defaultJwtToken));

            jwtTokenService.delete(TestJwtTokenBuilder.ID);

            verify(jwtTokenRepository).deleteById(TestJwtTokenBuilder.ID);
        }
    }
}
