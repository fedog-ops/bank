package com.eagle.bank_api.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String id;
    private String name;
    private AddressResponse address;
    private String number;
    private String email;
    private String createdAt;
    private String updatedAt;


    @Data
    @Builder
    public static class AddressResponse {
        private String line1;
        private String line2;
        private String line3;
        private String town;
        private String county;
        private String postcode;
    }
}
