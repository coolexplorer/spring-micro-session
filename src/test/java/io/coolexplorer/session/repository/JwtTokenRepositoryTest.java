package io.coolexplorer.session.repository;

import io.coolexplorer.session.config.TestRedisConfig;
import io.coolexplorer.session.model.JwtToken;
import io.coolexplorer.test.builder.TestJwtTokenBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@Tag("embedded-redis-test")
@SpringBootTest(classes = TestRedisConfig.class)
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yaml")
public class JwtTokenRepositoryTest {
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    JwtToken defaultJwtToken;

    @BeforeEach
    void setUp() {
        defaultJwtToken = TestJwtTokenBuilder.defaultJwtToken(TestJwtTokenBuilder.ID);
        jwtTokenRepository.save(defaultJwtToken);
    }

    @Nested
    @DisplayName("JwtToken Cache Creation")
    class JwtTokenCreationTest {
        @Test
        @DisplayName("Success")
        void testJwtTokenCacheCreate() {
            String id = UUID.randomUUID().toString();
            JwtToken jwtToken = TestJwtTokenBuilder.defaultJwtToken(id, 2L);
            JwtToken createdJwtToken = jwtTokenRepository.save(jwtToken);

            assertThat(createdJwtToken.getJwtToken()).isNotNull().isEqualTo(jwtToken.getJwtToken());
        }
    }

    @Nested
    @DisplayName("JwtToken Retrieve")
    class JwtTokenRetrieveTest {
        @Test
        @DisplayName("Success with Id")
        void testJwtTokenCacheRetrieve() {
            JwtToken jwtToken = jwtTokenRepository.findById(TestJwtTokenBuilder.ID).orElse(null);

            assertThat(jwtToken.getJwtToken()).isNotNull().isEqualTo(defaultJwtToken.getJwtToken());
        }

        @Test
        @DisplayName("Success with account id")
        void testJwtTokenCacheRetrieveWithAccountId() {
            JwtToken jwtToken = jwtTokenRepository.findJwtTokenByAccountId(TestJwtTokenBuilder.ACCOUNT_ID);

            assertThat(jwtToken.getJwtToken()).isNotNull().isEqualTo(defaultJwtToken.getJwtToken());
        }
    }

    @Nested
    @DisplayName("JwtToken Cache update")
    class JwtTokenCacheUpdateTest {
        @Test
        @DisplayName("Success")
        void testJwtTokenCacheUpdate() {
            JwtToken temp = TestJwtTokenBuilder.defaultJwtToken(UUID.randomUUID().toString(), 3L);

            jwtTokenRepository.save(temp);

            String newToken = UUID.randomUUID().toString();
            temp.setJwtToken(newToken);

            JwtToken updatedJwtToken = jwtTokenRepository.save(temp);

            assertThat(updatedJwtToken.getJwtToken()).isNotNull().isEqualTo(newToken);
        }
    }

    @Nested
    @DisplayName("JwtToken Cache Deletion")
    class JwtTokenCacheDeletionTest {
        @Test
        @DisplayName("Success")
        void testJwtTokenCacheDeletionTest() {
            String id = UUID.randomUUID().toString();
            JwtToken temp = TestJwtTokenBuilder.defaultJwtToken(id, 4L);

            jwtTokenRepository.save(temp);
            jwtTokenRepository.deleteById(id);

            JwtToken jwtToken = jwtTokenRepository.findById(id).orElse(null);

            assertThat(jwtToken).isNull();
        }
    }
}
