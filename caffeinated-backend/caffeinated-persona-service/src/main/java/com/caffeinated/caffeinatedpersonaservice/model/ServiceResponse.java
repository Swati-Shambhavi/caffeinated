package com.caffeinated.caffeinatedpersonaservice.model;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {
	private Object data;
	private Map<String,String> error;
}
