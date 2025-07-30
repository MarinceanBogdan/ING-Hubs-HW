package com.example.product_management.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "purchases")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NonNull
    private Product product;

    @NonNull
    private int amount;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}
