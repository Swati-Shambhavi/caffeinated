package com.caffeinated.cartexpressoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String email;
    private String role;
    private String name;
    private String mobileNumber;
    private AddressDto address;
    private CartResponse cart;
    @JsonProperty("address")
    public void setAddress(AddressDto address) {
        this.address = address;
    }
        }
