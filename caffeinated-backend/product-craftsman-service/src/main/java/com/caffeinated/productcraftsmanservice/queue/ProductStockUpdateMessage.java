package com.caffeinated.productcraftsmanservice.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStockUpdateMessage implements Serializable {
    private Integer productId;
    private Integer quantity;
}

