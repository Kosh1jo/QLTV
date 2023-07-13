package com.example.QLTV.Dto;

import com.example.QLTV.Entity.Role;

import com.example.QLTV.Validation.EmailNotExists;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ReaderDto {
    private int Id;
    @Email(message = "Email isn't true format")
    @NotBlank(message = "Email mustn't be null value")
    @EmailNotExists
    private String email;
    @NotBlank(message = "The name mustn't be null value")
    private String name;
    @NotBlank(message = "The password mustn't be null value")
    private String password;
    @NotBlank(message = "The phone mustn't be null value")
    private String phone;
    private boolean active;
    @Enumerated(EnumType.STRING)
    private Role roles;
}
