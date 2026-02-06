package org.abrar.storemanagement.repository;

import org.abrar.storemanagement.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    @Query("SELECT p, SUM(oi.quantity) AS soldQuantity FROM OrderItem oi JOIN oi.productVariant v JOIN v.product p JOIN oi.order o WHERE o.orderDateAndTime >= :dateAndTime GROUP BY p ORDER BY soldQuantity DESC")
    List<Object[]> findTopSellingProducts(@Param("dateAndTime") LocalDateTime fromDateAndTime);
}

