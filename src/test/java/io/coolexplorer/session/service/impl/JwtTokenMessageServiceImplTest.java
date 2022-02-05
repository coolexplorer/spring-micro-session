package io.coolexplorer.session.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.coolexplorer.session.controller.SpringBootWebMvcTestSupport;
import io.coolexplorer.session.message.JwtTokenMessage;
import io.coolexplorer.session.service.JwtTokenService;
import io.coolexplorer.session.topic.JwtTokenTopic;
import io.coolexplorer.test.builder.TestJwtTokenBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@EmbeddedKafka
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yaml")
public class JwtTokenMessageServiceImplTest extends SpringBootWebMvcTestSupport {
    @MockBean
    private JwtTokenService jwtTokenService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker = new EmbeddedKafkaBroker(
            2,
            true,
            2,
            JwtTokenTopic.TOPIC_CREATE_JWT_TOKEN,
            JwtTokenTopic.TOPIC_REQUEST_JWT_TOKEN,
            JwtTokenTopic.TOPIC_UPDATE_JWT_TOKEN,
            JwtTokenTopic.TOPIC_DELETE_JWT_TOKEN,
            JwtTokenTopic.TOPIC_REPLY_JWT_TOKEN
    );

    private Producer<String, Object> producer;

    @BeforeEach
    void setUp() {
        producer = configEmbeddedKafkaProducer();
    }

    @AfterEach
    void tearDown() {
        producer.close();
    }

    private Producer<String, Object> configEmbeddedKafkaProducer() {
        Map<String, Object> producerProperties = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        return new DefaultKafkaProducerFactory<>(producerProperties, new StringSerializer(), new JsonSerializer<>()).createProducer();
    }

    @Nested
    @DisplayName("JwtToken Cache Creation Test")
    class JwtTokenCacheCreationTest {
        @Test
        @DisplayName("Success")
        void testCreateMessageForJwtTokenCache() {
            JwtTokenMessage.CreateMessage createMessage = new JwtTokenMessage.CreateMessage();
            createMessage.setAccountId(1L);
            createMessage.setJwtToken("test-jwtToken");
            createMessage.setExpiration(120L);

            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(JwtTokenTopic.TOPIC_CREATE_JWT_TOKEN, createMessage);
            producer.send(producerRecord);
            producer.flush();

            when(jwtTokenService.create(any())).thenReturn(TestJwtTokenBuilder.defaultJwtToken());

            verify(jwtTokenService, timeout(10000L).times(1)).create(any());

            producer.close();
        }
    }

    @Nested
    @DisplayName("JwtToken Cache Update Test")
    class JwtTokenCacheUpdateTest {
        @Test
        @DisplayName("Success")
        void testUpdateMessageForJwtTokenCache() {
            JwtTokenMessage.UpdateMessage updateMessage = new JwtTokenMessage.UpdateMessage();
            updateMessage.setAccountId(1L);
            updateMessage.setJwtToken("test-jwtToken");
            updateMessage.setExpiration(120L);

            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(JwtTokenTopic.TOPIC_UPDATE_JWT_TOKEN, updateMessage);
            producer.send(producerRecord);
            producer.flush();

            when(jwtTokenService.update(any())).thenReturn(TestJwtTokenBuilder.defaultJwtToken());

            verify(jwtTokenService, timeout(10000L).times(1)).update(any());

            producer.close();
        }
    }

    @Nested
    @DisplayName("JwtToken Cache Request Test")
    class JwtTokenCacheRequestTest {
        @Test
        @DisplayName("Success")
        void testRequestMessageForJwtTokenCache() {
            JwtTokenMessage.RequestMessage requestMessage = new JwtTokenMessage.RequestMessage();
            requestMessage.setAccountId(1L);

            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(JwtTokenTopic.TOPIC_REQUEST_JWT_TOKEN, requestMessage);
            producer.send(producerRecord);
            producer.flush();

            when(jwtTokenService.getToken(anyLong())).thenReturn(TestJwtTokenBuilder.defaultJwtToken());

            verify(jwtTokenService, timeout(10000L).times(1)).getToken(anyLong());
        }
    }

    @Nested
    @DisplayName("JwtToken Cache Delete Test")
    class JwtTokenCacheDeleteTest {
        @Test
        @DisplayName("Success")
        void testDeleteMessageForJwtTokenCache() {
            JwtTokenMessage.DeleteMessage deleteMessage = new JwtTokenMessage.DeleteMessage();
            deleteMessage.setAccountId(1L);

            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(JwtTokenTopic.TOPIC_DELETE_JWT_TOKEN, deleteMessage);
            producer.send(producerRecord);
            producer.flush();

            doNothing().when(jwtTokenService).delete(anyLong());

            verify(jwtTokenService, timeout(10000L).times(1)).delete(anyLong());
        }
    }
}
