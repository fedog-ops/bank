package com.eagle.bank_api.user.dto;

import com.eagle.bank_api.user.entity.Address;
import com.eagle.bank_api.user.entity.User;

import java.time.ZoneOffset;

public class UserMapper {

    public static User toEntity(CreateUserRequest request) {
        if (request == null) {
            return null;
        }

        Address address = Address.builder()
                .line1(request.getAddress().getLine1())
                .line2(request.getAddress().getLine2())
                .line3(request.getAddress().getLine3())
                .town(request.getAddress().getTown())
                .county(request.getAddress().getCounty())
                .postcode(request.getAddress().getPostcode())
                .build();

        return User.builder()
                .name(request.getName())
                .number(request.getNumber())
                .email(request.getEmail())
                .address(address)
                .build();
    }

    public static UserResponse toDto(User user) {
        if (user == null) {
            return null;
        }

        UserResponse.AddressResponse addressResponse = UserResponse.AddressResponse.builder()
                .line1(user.getAddress().getLine1())
                .line2(user.getAddress().getLine2())
                .line3(user.getAddress().getLine3())
                .town(user.getAddress().getTown())
                .county(user.getAddress().getCounty())
                .postcode(user.getAddress().getPostcode())
                .build();

        return UserResponse.builder()
                .id(user.getUserId())
                .name(user.getName())
                .number(user.getNumber())
                .email(user.getEmail())
                .address(addressResponse)
                .createdAt(user.getCreatedAt() != null ? String.valueOf(user.getCreatedAt().atOffset(ZoneOffset.UTC)) : null)
                .updatedAt(user.getUpdatedAt() != null ? String.valueOf(user.getUpdatedAt().atOffset(ZoneOffset.UTC)) : null)
                .build();
    }
}
