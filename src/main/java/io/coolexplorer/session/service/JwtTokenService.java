package io.coolexplorer.session.service;

import io.coolexplorer.session.model.JwtToken;

public interface JwtTokenService {
    Long create(JwtToken token);
    JwtToken get(Long id);
}
