package com.caffeinated.caffeinatedpersonaservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private int addressId;

    @NotBlank(message = "Address1 must not be blank")
    @Size(min = 5, message = "Address1 must be at least 5 characters long")
    private String address1;

    private String address2;

    @NotBlank(message = "City must not be blank")
    @Size(min = 5, message = "City must be at least 5 characters long")
    private String city;

    @NotBlank(message = "State must not be blank")
    @Size(min = 5, message = "State must be at least 5 characters long")
    private String state;

    @NotBlank(message = "Pin Code must not be blank")
    private String pinCode;

    @NotBlank(message = "Country must not be blank")
    private String country;
}
