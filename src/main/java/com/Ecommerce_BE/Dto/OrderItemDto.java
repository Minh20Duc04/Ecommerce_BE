package com.Ecommerce_BE.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@Setter
public class OrderItemDto {

    private Long id;
    private int quantity;
    private BigDecimal price;
    private String status;
    private UserDto user;
    private ProductDto product;
    private LocalDateTime createdAt;

}
