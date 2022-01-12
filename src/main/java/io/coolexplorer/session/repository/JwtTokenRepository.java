package io.coolexplorer.session.repository;

import io.coolexplorer.session.model.JwtToken;
import org.springframework.data.repository.CrudRepository;

public interface JwtTokenRepository extends CrudRepository<JwtToken, Long> {

}
