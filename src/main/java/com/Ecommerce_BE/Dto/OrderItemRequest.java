package com.Ecommerce_BE.Dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class OrderItemRequest {

    private Long productId;
    private int quantity;

}
