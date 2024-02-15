package com.caffeinated.caffeinatedpersonaservice.util;

import com.caffeinated.caffeinatedpersonaservice.entity.Address;
import com.caffeinated.caffeinatedpersonaservice.entity.User;
import com.caffeinated.caffeinatedpersonaservice.model.AddressDto;
import com.caffeinated.caffeinatedpersonaservice.model.UserProfileDto;

public class MapMeUp {
    public static UserProfileDto toUserProfileDto(User entity) {
        if (entity == null) {
            return null;
        }
        AddressDto addressDto = toAddressDto(entity.getAddress());
        return UserProfileDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .role(entity.getRole())
                .name(entity.getName())
                .mobileNumber(entity.getMobileNumber())
                .address(addressDto)
                .build();
    }

    public static AddressDto toAddressDto(Address entity) {
        if (entity == null) {
            return null;
        }
        return AddressDto.builder()
                .addressId(entity.getId())
                .address1(entity.getAddress1())
                .address2(entity.getAddress2())
                .city(entity.getCity())
                .state(entity.getState())
                .pinCode(entity.getPinCode())
                .country(entity.getCountry())
                .build();
    }
}

