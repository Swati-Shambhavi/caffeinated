package com.caffeinated.cartexpressoservice.model.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderRequest {
    private String amount;
    private String currency;
}
