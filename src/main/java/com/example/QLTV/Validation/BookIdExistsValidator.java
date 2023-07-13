package com.example.QLTV.Validation;

import com.example.QLTV.Service.BookService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class BookIdExistsValidator implements ConstraintValidator<BookIdExists,Integer> {
    @Autowired
    private BookService bookService;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(id)){
            return true;
        }
        return bookService.isBookExistsByID(id);
    }
}
