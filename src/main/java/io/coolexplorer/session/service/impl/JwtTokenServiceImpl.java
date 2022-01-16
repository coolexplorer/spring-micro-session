package io.coolexplorer.session.service.impl;

import io.coolexplorer.session.model.JwtToken;
import io.coolexplorer.session.repository.JwtTokenRepository;
import io.coolexplorer.session.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    private final JwtTokenRepository tokenRepository;

    @Override
    @Transactional
    public JwtToken create(JwtToken token) {
        JwtToken retrievedToken = getTokenByAccountId(token.getAccountId());

        if (retrievedToken != null) {
            token.setId(retrievedToken.getId());
        }

        token.setUpdatedAt(LocalDateTime.now());
        return tokenRepository.save(token);
    }

    @Override
    public JwtToken update(JwtToken token) {
        return create(token);
    }

    @Override
    public JwtToken getToken(String id) {
        return tokenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found data. Id: " + id));
    }

    @Override
    public JwtToken getTokenByAccountId(Long accountId) {
        return tokenRepository.findJwtTokenByAccountId(accountId);
    }

    @Override
    public void delete(String id) {
        tokenRepository.deleteById(id);
    }
}
