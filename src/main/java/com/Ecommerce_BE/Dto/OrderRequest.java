package com.Ecommerce_BE.Dto;

import com.Ecommerce_BE.Model.Payment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderRequest {

    private BigDecimal totalCost;
    private List<OrderItemRequest> items;
    private Payment paymentInfo;

}