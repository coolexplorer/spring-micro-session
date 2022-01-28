package io.coolexplorer.session.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.coolexplorer.session.message.JwtTokenMessage;
import io.coolexplorer.session.model.JwtToken;
import io.coolexplorer.session.service.JwtTokenMessageService;
import io.coolexplorer.session.service.JwtTokenService;
import io.coolexplorer.session.topic.JwtTokenTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenMessageServiceImpl implements JwtTokenMessageService {
    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @Override
    @KafkaListener(
            topics = JwtTokenTopic.TOPIC_CREATE_JWT_TOKEN,
            groupId = "${kafka.consumer.groupId}",
            containerFactory = "kafkaJwtTokenListenerContainerFactory")
    public void listenCreateJwtTokenCache(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException {
        LOGGER.debug("received message from '{}' : {}", record.topic(), record.value());

        JwtTokenMessage.CreateMessage createMessage = objectMapper.readValue(record.value(), JwtTokenMessage.CreateMessage.class);
        JwtToken jwtToken = jwtTokenService.create(modelMapper.map(createMessage, JwtToken.class));

        LOGGER.debug("created JwtToken Cache '{}' : {}", jwtToken.getId(), jwtToken.getJwtToken());

        ack.acknowledge();
    }

    @Override
    @KafkaListener(
            topics = JwtTokenTopic.TOPIC_REQUEST_JWT_TOKEN,
            groupId = "${kafka.consumer.groupId}",
            containerFactory = "kafkaJwtTokenListenerContainerFactory")
    @SendTo(JwtTokenTopic.TOPIC_REPLY_JWT_TOKEN)
    public JwtTokenMessage.JwtTokenInfo listenRetrieveJwtTokenCache(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException {
        LOGGER.debug("received message from '{}' : {}", record.topic(), record.value());

        JwtTokenMessage.RequestMessage retrieveMessage = objectMapper.readValue(record.value(), JwtTokenMessage.RequestMessage.class);
        JwtToken jwtToken = jwtTokenService.getToken(retrieveMessage.getAccountId());
        ack.acknowledge();

        LOGGER.debug("requested JwtToken Cache '{}' : {}", jwtToken.getId(), jwtToken.getJwtToken());

        return JwtTokenMessage.JwtTokenInfo.from(jwtToken, modelMapper);
    }

    @Override
    @KafkaListener(
            topics = JwtTokenTopic.TOPIC_UPDATE_JWT_TOKEN,
            groupId = "${kafka.consumer.groupId}",
            containerFactory = "kafkaJwtTokenListenerContainerFactory")
    public void listenUpdateJwtTokenCache(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException {
        LOGGER.debug("received message from '{}' : {}", record.topic(), record.value());

        JwtTokenMessage.UpdateMessage updateMessage = objectMapper.readValue(record.value(), JwtTokenMessage.UpdateMessage.class);
        JwtToken jwtToken = jwtTokenService.update(modelMapper.map(updateMessage, JwtToken.class));
        ack.acknowledge();

        LOGGER.debug("updated JwtToken Cache '{}' : {}", jwtToken.getId(), jwtToken.getJwtToken());
    }

    @Override
    @KafkaListener(
            topics = JwtTokenTopic.TOPIC_DELETE_JWT_TOKEN,
            groupId = "${kafka.consumer.groupId}",
            containerFactory = "kafkaJwtTokenListenerContainerFactory")
    public void listenDeleteJwtTokenCache(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException {
        LOGGER.debug("received message from '{}' : {}", record.topic(), record.value());

        JwtTokenMessage.DeleteMessage deleteMessage = objectMapper.readValue(record.value(), JwtTokenMessage.DeleteMessage.class);
        jwtTokenService.delete(deleteMessage.getId());
        ack.acknowledge();
    }
}
