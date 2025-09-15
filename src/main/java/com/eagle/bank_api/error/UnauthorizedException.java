package com.eagle.bank_api.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnauthorizedException extends RuntimeException {
    private String message;

    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
    }
}