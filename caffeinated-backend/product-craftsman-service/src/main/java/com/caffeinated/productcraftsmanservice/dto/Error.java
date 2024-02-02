package com.caffeinated.productcraftsmanservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Error {
    private String code;
    private String message;
    private Map<String, String> errors;
}
