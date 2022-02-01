package io.coolexplorer.session.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.coolexplorer.session.dto.SessionDTO;
import io.coolexplorer.session.message.SessionMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

public interface SessionMessageService {
    void listenCreateSession(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException;
    SessionMessage.SessionInfo listenRetrieveSession(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException;
    void listenUpdateSession(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException;
    void listenDeleteSession(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException;
}
