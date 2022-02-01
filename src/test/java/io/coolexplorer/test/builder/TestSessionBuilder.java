package io.coolexplorer.test.builder;

import io.coolexplorer.session.model.Session;

import java.util.UUID;

public class TestSessionBuilder {
    public static String ID = UUID.randomUUID().toString();
    public static Long ACCOUNT_ID = 1L;
    public static String VALUES = "{\"orderCount\":1}";
    public static Long EXPIRATION = 100L;

    public static Session defaultSession() {
        return new Session()
                .setAccountId(ACCOUNT_ID)
                .setValues(VALUES);
    }

    public static Session defaultSession(String id) {
        return defaultSession()
                .setId(id);
    }
}
