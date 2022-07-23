package com.example.socialnetworkapp.configuration.validator;

import com.example.socialnetworkapp.configuration.AppConfiguration;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@RequiredArgsConstructor
public class BirthdayValidator implements ConstraintValidator<ValidBirthday, LocalDate> {

    private final AppConfiguration appConfiguration;

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate current = LocalDate.now();
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (date.isAfter(current.minusYears(appConfiguration.getAgeMinimum()))){
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Invalid birthday, age minimum: " + appConfiguration.getAgeMinimum())
                    .addConstraintViolation();
            return false;
        } else if (date.isBefore(current.minusYears(appConfiguration.getAgeMaximum()))){
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                            "Invalid birthday, age maximum: " + appConfiguration.getAgeMaximum())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
