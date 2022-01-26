package io.coolexplorer.session.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.coolexplorer.session.dto.SessionDTO;

public interface SessionMessageService {
    void listenCreateSession(String message) throws JsonProcessingException;
}
