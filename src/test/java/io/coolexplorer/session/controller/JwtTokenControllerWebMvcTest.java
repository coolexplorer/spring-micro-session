package io.coolexplorer.session.controller;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yaml")
public class JwtTokenControllerWebMvcTest extends SpringBootWebMvcTestSupport {

    @BeforeEach
    void setUp() throws Exception {
        super.setUp();
    }

    @Nested
    @DisplayName("JwtToken Cache Creation Test")
    class JwtTokenCacheCreationTest {
        @Test
        @DisplayName("Success")
        void testJwtTokenCacheCreate() {

        }
    }
}
