package com.caffeinated.cartexpressoservice.model.externalservice;

import com.caffeinated.cartexpressoservice.model.ErrorData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private ProductResponseData data;
    private ErrorData error;
}