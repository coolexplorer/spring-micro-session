package io.coolexplorer.session.service.impl;


import io.coolexplorer.session.message.SessionMessage;
import io.coolexplorer.session.service.SessionService;
import io.coolexplorer.session.topic.SessionTopic;
import io.coolexplorer.test.builder.TestSessionBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@Tag("embedded-kafka-test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@EmbeddedKafka
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yaml")
public class SessionMessageServiceImplTest {
    @MockBean
    private SessionService sessionService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker = new EmbeddedKafkaBroker(
            2,
            true,
            2,
            SessionTopic.TOPIC_CREATE_SESSION,
            SessionTopic.TOPIC_REQUEST_SESSION,
            SessionTopic.TOPIC_UPDATE_SESSION,
            SessionTopic.TOPIC_DELETE_SESSION,
            SessionTopic.TOPIC_REPLY_SESSION
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
    @DisplayName("Session Cache Creation Message Test")
    class SessionCacheCreationTest {
        @Test
        @DisplayName("Success")
        void testCreateMessageForSessionCache() {
            SessionMessage.CreateMessage createMessage = new SessionMessage.CreateMessage();
            createMessage.setAccountId(TestSessionBuilder.ACCOUNT_ID);
            createMessage.setValues(TestSessionBuilder.VALUES);
            createMessage.setExpiration(120L);

            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(SessionTopic.TOPIC_CREATE_SESSION, createMessage);
            producer.send(producerRecord);
            producer.flush();

            when(sessionService.create(any())).thenReturn(TestSessionBuilder.defaultSession(TestSessionBuilder.ID));

            verify(sessionService, timeout(10000L).times(1)).create(any());
        }
    }

    @Nested
    @DisplayName("Session Cache Update Message Test")
    class SessionCacheUpdateTest {
        @Test
        @DisplayName("Success")
        void testUpdateMessageForSessionCache() {
            SessionMessage.UpdateMessage updateMessage = new SessionMessage.UpdateMessage();
            updateMessage.setAccountId(TestSessionBuilder.ACCOUNT_ID);
            updateMessage.setValues(TestSessionBuilder.VALUES);
            updateMessage.setExpiration(120L);

            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(SessionTopic.TOPIC_UPDATE_SESSION, updateMessage);
            producer.send(producerRecord);
            producer.flush();

            when(sessionService.update(any())).thenReturn(TestSessionBuilder.defaultSession(TestSessionBuilder.ID));

            verify(sessionService, timeout(10000L).times(1)).update(any());
        }
    }

    @Nested
    @DisplayName("Session Cache Request Message Test")
    class SessionCacheRequestTest {
        @Test
        @DisplayName("Success")
        void testRequestMessageForSessionCache() {
            SessionMessage.RequestMessage requestMessage = new SessionMessage.RequestMessage();
            requestMessage.setAccountId(TestSessionBuilder.ACCOUNT_ID);

            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(SessionTopic.TOPIC_REQUEST_SESSION, requestMessage);
            producer.send(producerRecord);
            producer.flush();

            when(sessionService.getSession(anyLong())).thenReturn(TestSessionBuilder.defaultSession(TestSessionBuilder.ID));

            verify(sessionService, timeout(10000L).times(1)).getSession(anyLong());
        }
    }

    @Nested
    @DisplayName("Session Cache Deletion Message Test")
    class SessionCacheDeletionTest {
        @Test
        @DisplayName("Success")
        void testDeleteMessageForSessionCache() {
            SessionMessage.DeleteMessage deleteMessage = new SessionMessage.DeleteMessage();
            deleteMessage.setAccountId(TestSessionBuilder.ACCOUNT_ID);

            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(SessionTopic.TOPIC_DELETE_SESSION, deleteMessage);
            producer.send(producerRecord);
            producer.flush();

            doNothing().when(sessionService).delete(anyLong());

            verify(sessionService, timeout(10000L).times(1)).delete(anyLong());
        }
    }
}
