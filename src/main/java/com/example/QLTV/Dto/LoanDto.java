package com.example.QLTV.Dto;

import com.example.QLTV.Validation.DateFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class LoanDto {
    private int Id;
    private String readerName;
    private String bookTitle;
    @DateFormat
    @NotBlank(message = "The loanDate mustn't be null value")
    private String loanDate;
    @DateFormat
    @NotBlank(message = "The dueDate mustn't be null value")
    private String dueDate;
    private String returned;
}
