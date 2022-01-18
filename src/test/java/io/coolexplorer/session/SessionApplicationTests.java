package io.coolexplorer.session;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static io.swagger.v3.oas.integration.StringOpenApiConfigurationLoader.LOGGER;

@Slf4j
@SpringBootTest
class SessionApplicationTests {

	@Test
	@Disabled("Disabled")
	void contextLoads() {
		LOGGER.debug("SpringBoot test contextLoads()");
	}

}
