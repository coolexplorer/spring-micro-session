package io.coolexplorer.session.service;

import io.coolexplorer.session.model.Session;

public interface SessionService {
    Session create(Session session);
    Session update(Session session);
    Session getSession(String id);
    Session getSession(Long AccountId);
    void delete(String id);
    void delete(Long accountId);
}
