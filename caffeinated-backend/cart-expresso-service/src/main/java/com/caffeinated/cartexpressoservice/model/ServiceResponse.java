package com.caffeinated.cartexpressoservice.model;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {
	private Object data;
	private ErrorData error;
}
