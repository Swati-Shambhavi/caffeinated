package com.caffeinated.productcraftsmanservice.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {
	private Object data;
	private Error error;
}
