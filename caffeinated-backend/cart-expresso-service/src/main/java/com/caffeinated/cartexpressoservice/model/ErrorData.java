package com.caffeinated.cartexpressoservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@Builder
public class ErrorData {
        private String code;
        private String message;
        private Map<String, String> errors;
    }
