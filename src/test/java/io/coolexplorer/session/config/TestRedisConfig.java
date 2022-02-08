package io.coolexplorer.session.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

@Slf4j
@TestConfiguration
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yaml")
public class TestRedisConfig {
    @Value("${redis.port}")
    private int redisPort;

    private static RedisServer redisServer = null;

    @PostConstruct
    public void postConstruct() {
        if (Objects.isNull(redisServer)) {
            LOGGER.debug("Embedded Redis port : {}", redisPort);
            redisServer = new RedisServer(redisPort);
            redisServer.start();
        }
    }

    @PreDestroy
    public void preDestroy() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
