package com.example.product_management.Repository;

import com.example.product_management.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProducTRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    List<Product> findByCategory(String category);
}
