package com.Ecommerce_BE.Service.ServiceImp;

import com.Ecommerce_BE.Dto.OrderItemDto;
import com.Ecommerce_BE.Dto.OrderRequest;
import com.Ecommerce_BE.Dto.Response;
import com.Ecommerce_BE.Enum.OrderStatus;
import com.Ecommerce_BE.Exception.NotFoundException;
import com.Ecommerce_BE.Mapper.EntityDtoMapper;
import com.Ecommerce_BE.Model.Order;
import com.Ecommerce_BE.Model.OrderItem;
import com.Ecommerce_BE.Model.Product;
import com.Ecommerce_BE.Model.User;
import com.Ecommerce_BE.Repository.OrderItemRepository;
import com.Ecommerce_BE.Repository.OrderRepository;
import com.Ecommerce_BE.Repository.ProductRepository;
import com.Ecommerce_BE.Service.OrderItemService;
import com.Ecommerce_BE.Service.UserService;
import com.Ecommerce_BE.Specification.OrderItemSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemServiceImp implements OrderItemService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemSpecification orderItemSpecification;
    private final EntityDtoMapper entityDtoMapper;


    @Override
    public Response placeOrder(OrderRequest orderRequest) {
        //lay nguoi dung hien tai
        User user = userService.getLoginUser();

        //chuyen products thanh cac orderItems
        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(orderItemRequest -> {
                    Product product = productRepository.findById(orderItemRequest.getProductId()).orElseThrow(()->new NotFoundException("Product Not Found"));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setUser(user);
                    orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()))); //tinh theo gia tien cua product va so luong cua orderItem
                    orderItem.setProduct(product);
                    orderItem.setQuantity(orderItemRequest.getQuantity());
                    orderItem.setStatus(OrderStatus.PENDING);

                    return orderItem;
                })
                .collect(Collectors.toList());

        //tinh tong tien bang 2 cach, lay tu gia tri nhap vao hoac lay tung cai price dua tren he thong
        BigDecimal totalPrice = (orderRequest.getTotalCost() != null && orderRequest.getTotalCost().compareTo(BigDecimal.ZERO) > 0)
                ? orderRequest.getTotalCost()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);

        //tao Order
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        //khi Order duoc luu thi OrderItem cung se duoc luu
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        return Response.builder()
                .status(200)
                .message("Order created successfully")
                .build();
    }

    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(()->new NotFoundException("OrderItem Not Found"));
        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepository.save(orderItem);

        return Response.builder()
                .status(200)
                .message("Order status updated successfully")
                .build();
    }

    @Override
    public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
        //filter dua tren status, startDate va endDate
        Specification<OrderItem> specification = Specification.where(OrderItemSpecification.hasStatus(status))
                .and(OrderItemSpecification.createdBetween(startDate,endDate))
                .and(OrderItemSpecification.hasItemId(itemId));

        //phan trang dua tren specification da tao
        Page<OrderItem> orderItemPage = orderItemRepository.findAll(specification, pageable);

        List<OrderItemDto> orderItems = orderItemPage.getContent().stream()
                .map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .orderItemList(orderItems)
                .totalPage(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .build();
    }
}
