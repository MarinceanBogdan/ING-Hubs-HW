package com.example.product_management.Repository;

import com.example.product_management.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducTRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    List<Product> findByCategory(String category);
}
