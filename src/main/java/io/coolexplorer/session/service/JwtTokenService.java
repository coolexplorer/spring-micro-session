package io.coolexplorer.session.service;

import io.coolexplorer.session.model.JwtToken;

public interface JwtTokenService {
    JwtToken create(JwtToken token);
    JwtToken update(JwtToken token);
    JwtToken getToken(String id);
    JwtToken getTokenByAccountId(Long accountId);
}
