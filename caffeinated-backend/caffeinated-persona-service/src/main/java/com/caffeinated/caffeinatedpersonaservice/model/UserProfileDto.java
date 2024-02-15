package com.caffeinated.caffeinatedpersonaservice.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfileDto {
    private Integer id;
    private String email;
    private String role;
    private String name;
    private String mobileNumber;
    private AddressDto address;
}
