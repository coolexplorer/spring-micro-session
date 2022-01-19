package io.coolexplorer.session.annotation;

import io.coolexplorer.session.annotation.validator.JsonStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = JsonStringValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonStringConstraint {
    String message() default "Invalid Json String type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
