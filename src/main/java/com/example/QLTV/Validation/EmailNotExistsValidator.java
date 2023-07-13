package com.example.QLTV.Validation;

import com.example.QLTV.Service.ReaderService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class EmailNotExistsValidator implements ConstraintValidator<EmailNotExists,String> {
    @Autowired
    ReaderService readerService;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)){
            return true;
        }
        return !readerService.isEmailExists(s);
    }
}
