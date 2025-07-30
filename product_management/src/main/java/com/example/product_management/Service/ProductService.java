package com.example.product_management.Service;

import com.example.product_management.Dto.ProductDto;
import com.example.product_management.Entity.Product;
import com.example.product_management.Repository.ProducTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class ProductService {

    @Autowired
    ProducTRepository productRepository;

    public void updateProduct(Product oldProduct, ProductDto product) {

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
}
