package io.coolexplorer.session.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.coolexplorer.session.message.SessionMessage;
import io.coolexplorer.session.model.Session;
import io.coolexplorer.session.service.SessionMessageService;
import io.coolexplorer.session.service.SessionService;
import io.coolexplorer.session.topic.SessionTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionMessageServiceImpl implements SessionMessageService {
    private final SessionService sessionService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @Override
    @KafkaListener(
            topics = SessionTopic.TOPIC_CREATE_SESSION,
            groupId = "${kafka.consumer.groupId}",
            containerFactory = "kafkaSessionListenerContainerFactory")
    public void listenCreateSession(@Payload ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException {
        LOGGER.debug("received message from '{}' : {}", record.topic(), record.value());

        SessionMessage.CreateMessage createMessage = objectMapper.readValue(record.value(), SessionMessage.CreateMessage.class);
        Session createdSession = sessionService.create(modelMapper.map(createMessage, Session.class));

        ack.acknowledge();

        LOGGER.debug("Created session : {}", createdSession);
    }

    @Override
    @KafkaListener(
            topics = SessionTopic.TOPIC_REQUEST_SESSION,
            groupId = "${kafka.consumer.groupId}",
            containerFactory = "kafkaSessionListenerContainerFactory")
    @SendTo(SessionTopic.TOPIC_REPLY_SESSION)
    public SessionMessage.SessionInfo listenRetrieveSession(@Payload ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException {
        LOGGER.debug("received message from '{}' : {}", record.topic(), record.value());

        SessionMessage.RequestMessage requestMessage = objectMapper.readValue(record.value(), SessionMessage.RequestMessage.class);
        Session requestedSession = sessionService.getSession(requestMessage.getAccountId());
        ack.acknowledge();

        LOGGER.debug("Requested session : {}", requestedSession);

        return SessionMessage.SessionInfo.from(requestedSession, modelMapper);
    }

    @Override
    @KafkaListener(
            topics = SessionTopic.TOPIC_UPDATE_SESSION,
            groupId = "${kafka.consumer.groupId}",
            containerFactory = "kafkaSessionListenerContainerFactory")
    public void listenUpdateSession(@Payload ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException {
        LOGGER.debug("received message from '{}' : {}", record.topic(), record.value());

        SessionMessage.UpdateMessage updateMessage = objectMapper.readValue(record.value(), SessionMessage.UpdateMessage.class);
        Session updatedSession = sessionService.update(modelMapper.map(updateMessage, Session.class));

        ack.acknowledge();

        LOGGER.debug("Updated session : {}", updatedSession);
    }

    @Override
    @KafkaListener(
            topics = SessionTopic.TOPIC_DELETE_SESSION,
            groupId = "${kafka.consumer.groupId}",
            containerFactory = "kafkaSessionListenerContainerFactory")
    public void listenDeleteSession(@Payload ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException {
        LOGGER.debug("received message from '{}' : {}", record.topic(), record.value());

        SessionMessage.DeleteMessage deleteMessage = objectMapper.readValue(record.value(), SessionMessage.DeleteMessage.class);
        sessionService.delete(deleteMessage.getAccountId());

        ack.acknowledge();
    }
}
