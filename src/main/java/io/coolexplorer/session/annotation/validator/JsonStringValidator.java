package io.coolexplorer.session.annotation.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.coolexplorer.session.annotation.JsonStringConstraint;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

@Slf4j
public class JsonStringValidator implements ConstraintValidator<JsonStringConstraint, String> {

    @Override
    public void initialize(JsonStringConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (s != null) {
                final ObjectMapper mapper = new ObjectMapper();
                mapper.readTree(s);
            }
            return true;
        } catch (IOException e) {
            LOGGER.debug("String is not json format. {}", s);
            return false;
        }
    }
}
