package com.example.product_management.Service;

import com.example.product_management.Dto.ProductDto;
import com.example.product_management.Entity.Product;
import com.example.product_management.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public void updateProduct(Product oldProduct, ProductDto product) throws Exception {

        if(product.getPrice() < 0 ){
            throw new Exception("Price can not be negative!");
        }
        if(product.getQuantity() < 0) {
            throw new Exception("Quantity can not be negative!");
        }
        if(product.getName() != null) {
            oldProduct.setName(product.getName());
        }
        if(product.getCategory() != null) {
            oldProduct.setCategory(product.getCategory());
        }
        if(product.getDescription() != null) {
            oldProduct.setDescription(product.getDescription());
        }
        if(product.getPrice() != oldProduct.getPrice()) {
            oldProduct.setPrice(product.getPrice());
        }
        if(product.getQuantity() != oldProduct.getQuantity()) {
            oldProduct.setPrice((product.getPrice()));
        }

        productRepository.save(oldProduct);
    }

    @Transactional
    public void addProduct(Product product) throws Exception {

        if(product.getPrice() < 0) {
            throw new Exception("Price can not be negative!");
        }
        if(product.getQuantity() < 0) {
            throw new Exception("Quantity can not be negative!");
        }

        Product newProduct = new Product(product.getName(), product.getCategory(), product.getDescription(), product.getPrice(), product.getQuantity());
        productRepository.save(product);
    }
}
