package com.example.QLTV.Validation;

import com.example.QLTV.Service.ReaderService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class ReaderIdExistsValidator implements ConstraintValidator<ReaderIdExists,Integer> {
    @Autowired
    ReaderService readerService;
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(id)){
            return true;
        }
        return readerService.isReaderIdExist(id);
    }
}
