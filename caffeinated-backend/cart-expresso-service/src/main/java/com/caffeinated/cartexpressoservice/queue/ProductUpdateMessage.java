package com.caffeinated.cartexpressoservice.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateMessage implements Serializable {
    private Integer productId;
    private Integer quantity;
}
