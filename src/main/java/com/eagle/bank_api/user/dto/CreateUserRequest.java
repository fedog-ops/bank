package com.eagle.bank_api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "name required")
    private String name;

    @NotBlank(message = "phone number required" )
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "+44 phone number required")
    private String number;

    @NotBlank(message = "email required")
    @Email(message = "invalid email")
    private String email;

    @NotNull(message = "address required")
    private AddressRequest address;

}

