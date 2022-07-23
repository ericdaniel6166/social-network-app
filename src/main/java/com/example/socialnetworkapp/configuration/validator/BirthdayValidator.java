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
        if (date.isBefore(current.minusYears(appConfiguration.getAgeMinimum()))
                && date.isAfter(current.minusYears(appConfiguration.getAgeMaximum()))) {
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid birthday, age minimum: " +
                appConfiguration.getAgeMinimum() + ", age maximum: " + appConfiguration.getAgeMaximum())
                .addConstraintViolation();
        return false;
    }
}
