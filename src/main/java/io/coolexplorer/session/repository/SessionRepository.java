package io.coolexplorer.session.repository;

import io.coolexplorer.session.model.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, String> {
    Session findSessionByAccountId(Long accountId);
}
