package io.coolexplorer.session.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfig {
    private static final String ERROR_MESSAGE_SOURCE_PATH = "classpath:/messages/error_message";
    private static final String VALIDATION_MESSAGE_SOURCE_PATH = "classpath:/messages/validation_message";

    @Bean
    public MessageSource errorMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(ERROR_MESSAGE_SOURCE_PATH);
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean
    public MessageSource validationMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(VALIDATION_MESSAGE_SOURCE_PATH);
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean("errorMessageSourceAccessor")
    public MessageSourceAccessor errorMessageSourceAccessor() {
        return new MessageSourceAccessor(errorMessageSource());
    }

    @Bean("validationMessageSourceAccessor")
    public MessageSourceAccessor validationMessageSourceAccessor() {
        return new MessageSourceAccessor(validationMessageSource());
    }
}
