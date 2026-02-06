package org.abrar.storemanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name="order_date_time")
    private LocalDateTime orderDateAndTime;

    @Column(name="total_amount")
    private double totalAmount;

    @OneToMany(mappedBy ="order", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems;

    @PrePersist
    private void onCreate(){
        orderDateAndTime=LocalDateTime.now();
    }

}
