package com.example.socialnetworkapp.configuration.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class DateValidator implements ConstraintValidator<IsInRange, Date> {

    String isBefore;
    String isAfter;

    @Override
    public void initialize(IsInRange constraintAnnotation) {
        isBefore = constraintAnnotation.isBefore();
        isAfter = constraintAnnotation.isAfter();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
