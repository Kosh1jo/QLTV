package com.example.QLTV.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD,ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BookIdExistsValidator.class)
@Repeatable(BookIdExists.List.class)
public @interface BookIdExists {
    String message() default "Book id not exists !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    @Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List{
        BookIdExists[] value();
    }
}
