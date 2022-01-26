package io.coolexplorer.session.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.coolexplorer.session.message.SessionMessage;
import io.coolexplorer.session.service.SessionMessageService;
import io.coolexplorer.session.topic.SessionTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionMessageServiceImpl implements SessionMessageService {
    private final ObjectMapper objectMapper;
    @Override
    @KafkaListener(topics = SessionTopic.TOPIC_CREATE_SESSION, groupId = "${kafka.consumer.groupId}")
    public void listenCreateSession(String message) throws JsonProcessingException {
        SessionMessage.CreateMessage createMessage = objectMapper.readValue(message, SessionMessage.CreateMessage.class);
        LOGGER.debug("received message from 'create-session' : {}", createMessage);
    }
}
