package io.coolexplorer.session.repository;

import io.coolexplorer.session.model.JwtToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JwtTokenRepository extends CrudRepository<JwtToken, String> {
    JwtToken findJwtTokenByAccountId(Long accountId);
}
