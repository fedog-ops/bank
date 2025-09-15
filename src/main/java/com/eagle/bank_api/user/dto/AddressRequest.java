package com.eagle.bank_api.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequest {
    @NotNull
    private String line1;
    private String line2;
    private String line3;
    @NotNull
    private String town;
    @NotNull
    private String county;
    @NotNull
    private String postcode;

}
