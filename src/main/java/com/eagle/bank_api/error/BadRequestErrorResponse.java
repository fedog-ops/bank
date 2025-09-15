package com.eagle.bank_api.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadRequestErrorResponse {
    private String message;
    private List<ErrorDetail> details;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorDetail {
        private String field;
        private String message;
        private String type;
    }
}
