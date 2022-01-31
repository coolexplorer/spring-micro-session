package io.coolexplorer.session.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.coolexplorer.session.message.JwtTokenMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

public interface JwtTokenMessageService {
    void listenCreateJwtTokenCache(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException;
    JwtTokenMessage.JwtTokenInfo listenRetrieveJwtTokenCache(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException;
    void listenUpdateJwtTokenCache(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException;
    void listenDeleteJwtTokenCache(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException;
}
