package com.example.QLTV.Dto;

import com.example.QLTV.Validation.DateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BookDto {
    private int Id;
    @NotBlank(message = "The title mustn't be null value")
    private String title;
    @NotBlank(message = "The author mustn't be null value")
    private String author;
    @NotBlank(message ="The publicationYear mustn't be null value" )
    @DateFormat
    private String publicationYear;
}
