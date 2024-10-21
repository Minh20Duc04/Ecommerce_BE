package com.Ecommerce_BE.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Response<T> { //tao 1 trang thai tra ve chung cho cac Service

    private int status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    private String token;
    private String role;
    private String expirationTime;

    private int totalPage;
    private long totalElement;

    private T data;

    /*
    private AddressDto address;

    private UserDto user;
    private List<UserDto> userList;

    private CategoryDto category;
    private List<CategoryDto> categoryList;

    private ProductDto product;
    private List<ProductDto> productList;

    private OrderDto order;
    private List<OrderDto> orderList;

    private OrderItemDto orderItem;
    private List<OrderItemDto> orderItemList;
     */
}
