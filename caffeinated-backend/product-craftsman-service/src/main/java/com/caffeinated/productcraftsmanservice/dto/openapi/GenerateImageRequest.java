package com.caffeinated.productcraftsmanservice.dto.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateImageRequest {

    @NotBlank
    private String prompt;

    private String size;

    @Min(1)
    @Max(3)
    @JsonProperty("num_images")
    private int numImages;

}
