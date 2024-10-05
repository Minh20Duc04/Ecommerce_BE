package com.Ecommerce_BE.Specification;

import com.Ecommerce_BE.Enum.OrderStatus;
import com.Ecommerce_BE.Model.OrderItem;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
@Configuration
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

    //tim theo ngay
    public static Specification<OrderItem> createdBetween(LocalDateTime startDate, LocalDateTime endDate)
    {
        return ((root, query, criteriaBuilder) ->
        {
            if(startDate != null && endDate != null)
            {
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            }
            else if (startDate != null)
            {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            }
            else if (endDate != null)
            {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            }
            else
            {
                return null;
            }
        }
        );
    }

    //tim theo id
    public static Specification<OrderItem> hasItemId(Long itemId)
    {
        return (root, query, criteriaBuilder) ->
                itemId != null ? criteriaBuilder.equal(root.get("id"), itemId) : null;
    }

}
