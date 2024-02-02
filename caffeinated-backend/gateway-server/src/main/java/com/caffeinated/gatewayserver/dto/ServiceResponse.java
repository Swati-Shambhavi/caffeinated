package com.caffeinated.gatewayserver.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse {
    private Object data;
    private  Error error;

}
