package com.example.QLTV.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD,ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DateFormatValidator.class})
@Repeatable(DateFormat.List.class)
public @interface DateFormat {
    String format() default "yyyy-MM-dd";

    String message() default "Invalid date format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    @Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List{
        DateFormat[] value();
    }
}

