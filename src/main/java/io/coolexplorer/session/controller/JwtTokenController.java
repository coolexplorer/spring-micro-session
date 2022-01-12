package io.coolexplorer.session.controller;

import io.coolexplorer.session.dto.JwtTokenDTO;
import io.coolexplorer.session.model.JwtToken;
import io.coolexplorer.session.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class JwtTokenController {
    private final JwtTokenService jwtTokenService;

    @PostMapping("/token")
    public Long createToken(@RequestBody JwtTokenDTO.JwtTokenCreateRequest request) {
        JwtToken jwtToken = new JwtToken();
        jwtToken.setEmail(request.getEmail());
        jwtToken.setJwtToken(request.getJwtToken());

        return jwtTokenService.create(jwtToken);
    }

    @GetMapping("/token/{id}")
    public JwtTokenDTO.JwtTokenInfo getToken(@PathVariable("id") Long id) {
        JwtToken jwtToken = jwtTokenService.get(id);

        return new JwtTokenDTO.JwtTokenInfo()
                .setId(jwtToken.getId())
                .setEmail(jwtToken.getEmail())
                .setJwtToken(jwtToken.getJwtToken());
    }

}
