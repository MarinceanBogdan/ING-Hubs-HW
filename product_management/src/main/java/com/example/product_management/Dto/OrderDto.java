package com.example.product_management.Dto;

import com.example.product_management.Entity.Purchase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class OrderDto {
    private List<PurchaseDto> purchaseList;
}
