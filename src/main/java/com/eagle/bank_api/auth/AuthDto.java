package com.eagle.bank_api.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDto {
    @NotBlank(message = "email required")
    @Email(message = "invalid email")
    private String email;
}
