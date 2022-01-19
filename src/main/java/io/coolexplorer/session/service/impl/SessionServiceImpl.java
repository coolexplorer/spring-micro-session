package io.coolexplorer.session.service.impl;

import io.coolexplorer.session.exception.session.SessionNotFoundException;
import io.coolexplorer.session.model.Session;
import io.coolexplorer.session.repository.SessionRepository;
import io.coolexplorer.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public Session create(Session session) {
        Session retrievedSession = getSession(session.getAccountId());

        if (retrievedSession != null) {
            session.setId(retrievedSession.getId());
        }

        session.setUpdatedAt(LocalDateTime.now());
        return sessionRepository.save(session);
    }

    @Override
    public Session update(Session session) {
        return create(session);
    }

    @Override
    public Session getSession(String id) {
        return sessionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found session data " + id));
    }

    @Override
    public Session getSession(Long accountId) {
        return sessionRepository.findSessionByAccountId(accountId);
    }

    @Override
    public void delete(String id) {
        Session retrievedSession = getSession(id);

        if (retrievedSession == null) {
            throw new SessionNotFoundException();
        }

        sessionRepository.delete(retrievedSession);
    }
}
