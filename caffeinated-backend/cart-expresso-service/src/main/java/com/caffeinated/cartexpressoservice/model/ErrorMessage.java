package com.caffeinated.cartexpressoservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage{
private String code;
private String description;

}
