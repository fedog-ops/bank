package com.eagle.bank_api.user.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotBlank(message = "line 1 required")
    private String line1;
    private String line2;
    private String line3;
    @NotBlank(message = "town required")
    private String town;
    @NotBlank(message = "country required")
    private String county;
    @NotBlank(message = "postcode required")
    private String postcode;
}
