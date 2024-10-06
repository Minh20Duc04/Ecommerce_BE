package com.Ecommerce_BE.Mapper;
import com.Ecommerce_BE.Dto.*;
import com.Ecommerce_BE.Model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class EntityDtoMapper {

    //user entity to user Dto
    public UserDto mapUserToDtoBasic(User user)
    {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole().name())
                .name(user.getName())
                .build();
        return userDto;
    }

    //Address to Dto
    public AddressDto mapAddressToDtoBasic(Address address)
    {
        AddressDto addressDto = AddressDto.builder()
                .id(address.getId())
                .city(address.getCity())
                .state(address.getState())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .build();
        return addressDto;
    }

    //Category to Dto
    public CategoryDto mapCategoryToDtoBasic(Category category)
    {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
        return categoryDto;
    }

    //Product to ProductDto
    public ProductDto mapProductToDtoBasic(Product product)
    {
        ProductDto productDto = ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .build();
        return productDto;
    }

    //OrderItem to OrderItemDto
    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem)
    {
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .status(orderItem.getStatus().name())
                .price(orderItem.getPrice())
                .createdAt(orderItem.getCreatedAt())
                .build();
        return orderItemDto;
    }

    //User to UserDto plus Address
    public UserDto mapUserToUserDtoPlusAddress(User user)
    {
        log.info("mapUserToUserDtoPlusAddress is called");
        UserDto userDto = mapUserToDtoBasic(user);
        if(user.getAddress() != null)
        {
            System.out.println("ADDRESS IS NOT NULL");
            AddressDto addressDto = mapAddressToDtoBasic(user.getAddress());
            userDto.setAddress(addressDto);
        }
        return userDto;
    }

    //OrderItem to OrderItemDto plus Product
    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem)
    {
        OrderItemDto orderItemDto = mapOrderItemToDtoBasic(orderItem);
        if(orderItem.getProduct() != null)
        {
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }

    //OrderItem to OrderItemDto plus product and user
    public OrderItemDto mapOrderItemToDtoPlusProductAndUser(OrderItem orderItem)
    {
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem); //kiem tra Product luon bang phuong thuc nay, con User chua co phuong thuc nen se kiem tra cu the o duoi
        if(orderItem.getUser() != null)
        {
            UserDto userDto = mapUserToUserDtoPlusAddress(orderItem.getUser()); //kiem tra chi tiet address luon
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }

    //User to UserDto with Address and Order Items History
    public UserDto mapUserToDtoPlusAddressAndOrderHistory(User user)
    {
        UserDto userDto = mapUserToUserDtoPlusAddress(user);
        if(user.getOrderItemList() != null && !user.getOrderItemList().isEmpty())
        {
            userDto.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDtoPlusProduct)
                    .collect(Collectors.toList())
            );
        }
        return userDto;
    }


}
