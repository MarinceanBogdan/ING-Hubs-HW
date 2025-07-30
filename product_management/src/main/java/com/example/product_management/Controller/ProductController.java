package com.example.product_management.Controller;

import com.example.product_management.Dto.ProductDto;
import com.example.product_management.Entity.Product;
import com.example.product_management.Entity.User;
import com.example.product_management.Repository.ProducTRepository;
import com.example.product_management.Security.PermissionChecker;
import com.example.product_management.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    PermissionChecker permissionChecker;
    @Autowired
    ProducTRepository productRepository;
    @Autowired
    ProductService productService;

    @GetMapping(value = "/getProduct/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getProduct(@PathVariable String name, HttpServletRequest request){

        User requester = (User) request.getAttribute("user");
        if (requester == null || !permissionChecker.hasPermission(requester, "READ")) {
            return ResponseEntity.status(403).body(null);
        }

        List<Product> products = productRepository.findByName(name);

        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/getAllProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProduct( HttpServletRequest request){

        User requester = (User) request.getAttribute("user");
        if (requester == null || !permissionChecker.hasPermission(requester, "READ")) {
            return ResponseEntity.status(403).body(null);
        }

        List<Product> products = productRepository.findAll();

        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/getByCategory/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category, HttpServletRequest request){

        User requester = (User) request.getAttribute("user");
        if (requester == null || !permissionChecker.hasPermission(requester, "READ")) {
            return ResponseEntity.status(403).body(null);
        }

        List<Product> products = productRepository.findByCategory(category);

        return ResponseEntity.ok().body(products);
    }

    @PostMapping(value = "/addProduct", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProduct(@RequestBody Product product, HttpServletRequest request) {

        User requester = (User) request.getAttribute("user");
        if (requester == null || !permissionChecker.hasPermission(requester, "WRITE")) {
            return ResponseEntity.status(403).body(null);
        }

        Product newProduct = new Product(product.getName(), product.getCategory(), product.getDescription(), product.getPrice(), product.getQuantity());
        productRepository.save(product);

        return ResponseEntity.ok().body("New product added!");
    }

    @PutMapping(value = "/updateProduct/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDto product, HttpServletRequest request) throws Exception {
        User requester = (User) request.getAttribute("user");
        if (requester == null || !permissionChecker.hasPermission(requester, "WRITE")) {
            return ResponseEntity.status(403).body(null);
        }

        Product oldProduct =(Product) productRepository.findById(id).orElse(null);

        if(oldProduct == null) {
            throw new Exception("Product not found!");
        }

        productService.updateProduct(oldProduct, product);

        return ResponseEntity.ok().body("Product updated!");
    }
}
