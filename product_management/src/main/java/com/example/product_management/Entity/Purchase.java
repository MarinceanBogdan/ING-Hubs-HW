package com.example.product_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "purchases")
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int amount;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}
