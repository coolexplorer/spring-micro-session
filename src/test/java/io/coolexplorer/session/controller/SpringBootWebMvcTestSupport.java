package io.coolexplorer.session.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SpringBootWebMvcTestSupport {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Validator validator;

    @Autowired
    MessageSourceAccessor validationMessageSourceAccessor;

    void setUp() throws Exception {

    }

    String getValidationMessage(String messageKey) {
        return validationMessageSourceAccessor.getMessage(messageKey);
    }

    <T> Map<String, ConstraintViolation<T>> convertMap(Set<ConstraintViolation<T>> violations) {
        Map<String, ConstraintViolation<T>> map = new HashMap<>();

        for (ConstraintViolation violation: violations) {
            map.put(violation.getPropertyPath().toString(), violation);
        }

        return map;
    }
}
