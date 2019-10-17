package com.javalab.clothshop.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Column(name = "ship_date", nullable = false)
    private LocalDateTime shipDate;
    @Column(name = "cr_date", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;
    @Column(name = "complete", nullable = false)
    private boolean complete;
    @Singular
    @ManyToMany
    @JoinTable(
            name = "ORDER_PRODUCT",
            joinColumns = { @JoinColumn(name = "ORDERS.id") },
            inverseJoinColumns = { @JoinColumn(name = "PRODUCTS.id") }
    )
    private List<Product> items;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
