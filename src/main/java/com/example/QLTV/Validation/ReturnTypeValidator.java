package com.example.QLTV.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ReturnTypeValidator implements ConstraintValidator<ReturnType,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String> ReturnType = List.of("true,false");
        return ReturnType.contains(s);
    }
}
