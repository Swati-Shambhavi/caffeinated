package com.caffeinated.productcraftsmanservice.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProductCustomizationRequest {

    @NotEmpty
    @Size(min = 1, max = 5)
    private List<String> desiredEffects;

    @NotEmpty
    @Size(min = 1, max = 5)
    private List<String> preferredFlavors;

    private List<String> dietaryRestrictions;

    @NotBlank
    private String desiredFormat;

    @NotBlank
    private String caffeineLevel;

    @NotNull
    private Double budget;

    private String context;
}
