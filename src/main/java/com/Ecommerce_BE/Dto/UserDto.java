package com.Ecommerce_BE.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    private AddressDto address;
    private List<OrderItemDto> orderItemList;

}
