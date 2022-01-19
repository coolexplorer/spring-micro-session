package io.coolexplorer.session.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    JWT_TOKEN_NOT_FOUND("error.jwt.token.not.found"),
    SESSION_NOT_FOUND("error.session.not.found");

    private final String messageKey;

    ErrorCode(String messageKey) {
        this.messageKey = messageKey;
    }
}
