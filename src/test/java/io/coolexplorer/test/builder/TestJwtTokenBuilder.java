package io.coolexplorer.test.builder;

import io.coolexplorer.session.model.JwtToken;

import java.util.UUID;

public class TestJwtTokenBuilder {
    public static String ID = UUID.randomUUID().toString();
    public static Long ACCOUNT_ID = 1L;
    public static String JWT_TOKEN = UUID.randomUUID().toString();
    public static Long EXPIRATION = -1L;

    public static JwtToken defaultJwtToken() {
        return new JwtToken()
                .setAccountId(ACCOUNT_ID)
                .setJwtToken(JWT_TOKEN);
    }

    public static JwtToken defaultJwtToken(String id) {
        return defaultJwtToken()
                .setId(id);
    }

    public static JwtToken defaultJwtToken(String id, Long accountId) {
        return new JwtToken()
                .setId(id)
                .setAccountId(accountId)
                .setJwtToken(JWT_TOKEN);
    }
}
