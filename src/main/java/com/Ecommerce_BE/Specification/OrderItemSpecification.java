package com.Ecommerce_BE.Specification;

import com.Ecommerce_BE.Enum.OrderStatus;
import com.Ecommerce_BE.Model.OrderItem;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class OrderItemSpecification {
    //su dung Specification de tao truy van dong (dung khi can xu ly truy van phuc tap hon @Query)

    //tim theo status cua don hang
    public static Specification<OrderItem> hasStatus(OrderStatus status)
    {
        /*
        * root(OrderItem)
        * query(truy van)
        * criteriaBuilder(dieu kien va phep toan)
        */
        return ((root, query, criteriaBuilder) ->
                status != null ? criteriaBuilder.equal(root.get("status"), status) : null);
    }





}
