package io.coolexplorer.session.handler;

import io.coolexplorer.session.dto.ErrorResponse;
import io.coolexplorer.session.enums.ErrorCode;
import io.coolexplorer.session.exception.jwtToken.JwtTokenException;
import io.coolexplorer.session.exception.jwtToken.JwtTokenNotFoundException;
import io.coolexplorer.session.exception.session.SessionException;
import io.coolexplorer.session.exception.session.SessionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestResponseEntityExceptionHandler {
    private final MessageSourceAccessor errorMessageSourceAccessor;

    @ExceptionHandler(JwtTokenException.class)
    protected ResponseEntity<ErrorResponse> handlerJwtTokenException(JwtTokenException e) {
        LOGGER.error(e.getLocalizedMessage(), e);

        ErrorResponse response = createJwtTokenErrorMessage(e);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createJwtTokenErrorMessage(JwtTokenException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        if (e instanceof JwtTokenNotFoundException) {
            errorResponse = createErrorMessage(ErrorCode.JWT_TOKEN_NOT_FOUND);
        }

        return errorResponse;
    }

    @ExceptionHandler(SessionException.class)
    protected ResponseEntity<ErrorResponse> handlerSessionException(SessionException e) {
        LOGGER.error(e.getLocalizedMessage(), e);

        ErrorResponse response = createSessionErrorMessage(e);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createSessionErrorMessage(SessionException e) {
        ErrorResponse errorResponse = new ErrorResponse();

        if (e instanceof SessionNotFoundException) {
            errorResponse = createErrorMessage(ErrorCode.SESSION_NOT_FOUND);
        }

        return errorResponse;
    }

    private ErrorResponse createErrorMessage(ErrorCode errorCode) {
        return new ErrorResponse()
                .setCode(errorCode)
                .setDescription(getMessage(errorCode));
    }

    private String getMessage(ErrorCode errorCode) {
        return errorMessageSourceAccessor.getMessage(errorCode.getMessageKey());
    }
}
