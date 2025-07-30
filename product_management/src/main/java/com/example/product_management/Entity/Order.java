package com.example.product_management.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long order_id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NonNull
    private User customer;

    @OneToMany(mappedBy = ("order"), cascade = CascadeType.ALL)
    @NonNull
    private List<Purchase> purchaseList;

    @NonNull
    private float totalCost;

    private LocalDateTime order_date = LocalDateTime.now();
}
