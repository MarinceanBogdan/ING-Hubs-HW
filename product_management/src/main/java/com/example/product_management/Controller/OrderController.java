package com.example.product_management.Controller;

import com.example.product_management.Dto.OrderDto;
import com.example.product_management.Entity.Order;
import com.example.product_management.Entity.User;
import com.example.product_management.Security.PermissionChecker;
import com.example.product_management.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/orders")
@RestController
public class OrderController {
    @Autowired
    PermissionChecker permissionChecker;
    @Autowired
    OrderService orderService;

    @PostMapping(value = "/addOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addOrder(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        try {
            User requester = (User) request.getAttribute("user");
            if (requester == null || !permissionChecker.hasPermission(requester, "WRITE")) {
                return ResponseEntity.status(403).body(null);
            }

            orderService.addOrder(orderDto, requester);

            return ResponseEntity.ok().body("Order processed successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
