package com.Ecommerce_BE.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Long categoryId;

}
