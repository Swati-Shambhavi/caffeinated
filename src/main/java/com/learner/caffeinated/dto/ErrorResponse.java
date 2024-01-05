package com.learner.caffeinated.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
	private String code;
	private String message;
}
