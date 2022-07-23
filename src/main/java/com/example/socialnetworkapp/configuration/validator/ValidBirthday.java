package com.example.socialnetworkapp.configuration.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = BirthdayValidator.class)
@Documented
public @interface ValidBirthday {
    String message() default "Invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
