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
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public Validator validator;

    @Autowired
    public MessageSourceAccessor validationMessageSourceAccessor;

    void setUp() throws Exception {

    }

    public String getValidationMessage(String messageKey) {
        return validationMessageSourceAccessor.getMessage(messageKey);
    }

    public <T> Map<String, ConstraintViolation<T>> convertMap(Set<ConstraintViolation<T>> violations) {
        Map<String, ConstraintViolation<T>> map = new HashMap<>();

        for (ConstraintViolation violation: violations) {
            map.put(violation.getPropertyPath().toString(), violation);
        }

        return map;
    }
}
