package com.example.QLTV.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    private String format;
    @Override
    public void initialize(DateFormat constraintAnnotation) {
        format = constraintAnnotation.format();
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true; // Cho phép giá trị null (nếu có)
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // Không cho phép định dạng linh hoạt (strict parsing)
        try {
            sdf.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


}
