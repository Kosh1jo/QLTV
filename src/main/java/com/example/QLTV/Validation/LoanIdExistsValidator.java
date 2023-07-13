package com.example.QLTV.Validation;

import com.example.QLTV.Service.LoanService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class LoanIdExistsValidator implements ConstraintValidator<LoanIdExists,Integer> {
    @Autowired
    LoanService loanService;

    @Override
    public void initialize(LoanIdExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(id)){
            return true;
        }
        return loanService.isLoanExistsByID(id);
    }
}
