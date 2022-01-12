package io.coolexplorer.session.service.impl;

import io.coolexplorer.session.model.JwtToken;
import io.coolexplorer.session.repository.JwtTokenRepository;
import io.coolexplorer.session.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    private final JwtTokenRepository tokenRepository;

    @Override
    @Transactional
    public Long create(JwtToken token) {
        return tokenRepository.save(token).getId();
    }

    @Override
    public JwtToken get(Long id) {
        return tokenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found data. Id: " + id));
    }


}
