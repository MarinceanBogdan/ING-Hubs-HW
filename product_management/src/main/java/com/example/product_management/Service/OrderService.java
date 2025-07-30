package com.example.product_management.Service;

import com.example.product_management.Dto.OrderDto;
import com.example.product_management.Dto.PurchaseDto;
import com.example.product_management.Entity.Order;
import com.example.product_management.Entity.Product;
import com.example.product_management.Entity.Purchase;
import com.example.product_management.Entity.User;
import com.example.product_management.Repository.OrderRepository;
import com.example.product_management.Repository.ProducTRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProducTRepository productRepository;

    @Transactional
    public void addOrder(OrderDto order, User customer) {

        try {
        List<Purchase> purchaseList = new ArrayList<>();
        float totalCost = 0;
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        order.getPurchaseList().stream().forEach(p -> {
            Product newProduct = productRepository.findById(p.getProductId()).orElseThrow(() -> new RuntimeException("Product with id " + p.getProductId() + "not found"));
            if(p.getAmount() < 0 ) {
                throw new RuntimeException("Amount for product with id " + p.getProductId() + " cannot be negative!");
            }
            if(newProduct != null) {
                purchaseList.add(new Purchase(newProduct, p.getAmount()));
                newOrder.setTotalCost(newOrder.getTotalCost() + newProduct.getPrice() * p.getAmount());
            }
        });
        newOrder.setPurchaseList(purchaseList);

        orderRepository.save(newOrder);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
