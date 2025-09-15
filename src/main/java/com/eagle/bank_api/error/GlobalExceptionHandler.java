package com.eagle.bank_api.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<BadRequestErrorResponse.ErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> BadRequestErrorResponse.ErrorDetail.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .type(error.getCode() != null ? error.getCode() : "validation_error")
                        .build())
                .collect(Collectors.toList());

        BadRequestErrorResponse response = BadRequestErrorResponse.builder()
                .message("Validation failed")
                .details(details)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
