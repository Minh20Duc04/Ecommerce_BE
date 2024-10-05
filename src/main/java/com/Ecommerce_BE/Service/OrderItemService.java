package com.Ecommerce_BE.Service;

import com.Ecommerce_BE.Dto.OrderRequest;
import com.Ecommerce_BE.Dto.Response;
import com.Ecommerce_BE.Enum.OrderStatus;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface OrderItemService {

    Response placeOrder(OrderRequest orderRequest);

    Response updateOrderItemStatus(Long orderItemId, String status);

    Response filterOrderItems(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);


}
