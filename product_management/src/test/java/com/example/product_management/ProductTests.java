package com.example.product_management;

import com.example.product_management.Controller.ProductController;
import com.example.product_management.Entity.Product;
import com.example.product_management.Entity.User;
import com.example.product_management.Repository.ProductRepository;
import com.example.product_management.Repository.UserRepository;
import com.example.product_management.Security.PermissionChecker;
import com.example.product_management.Service.ProductService;
import com.example.product_management.Util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
@Import(SecurityTestConfig.class)
public class ProductTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PermissionChecker permissionChecker;

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private JwtUtil jwtUtil;

    private User getMockUser() {
        return new User();
    }

    @Test
    void getProductWithPermission() throws Exception {
        when(permissionChecker.hasPermission(any(User.class), Mockito.eq("READ"))).thenReturn(true);
        when(productRepository.findByName("Laptop")).thenReturn(Collections.singletonList(new Product()));

        mockMvc.perform(get("/products/getProduct/Laptop")
                .requestAttr("user", getMockUser()))
                .andExpect(status().isOk());
    }

    @Test
    void getProductWithoutPermission() throws Exception {
        when(permissionChecker.hasPermission(any(User.class), Mockito.eq("READ"))).thenReturn(false);

        mockMvc.perform(get("/products/getProduct/Laptop")
                .requestAttr("user", getMockUser()))
                .andExpect(status().isForbidden());
    }

    @Test
    void addProductWithPermission() throws Exception {
        when(permissionChecker.hasPermission(any(User.class), Mockito.eq("WRITE"))).thenReturn(true);

        mockMvc.perform(post("/products/addProduct")
                .requestAttr("user", getMockUser())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"name\":\"Laptop\",\"price\":1000}"))
                .andExpect(status().isOk())
                .andExpect(content().string("New product added!"));
    }

    @Test
    void addProductWithoutPermission() throws Exception {
        when(permissionChecker.hasPermission(any(User.class), Mockito.eq("WRITE"))).thenReturn(false);

        mockMvc.perform(post("/products/addProduct")
                        .requestAttr("user", getMockUser())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"name\":\"Laptop\",\"price\":1000}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateProductWithPermission() throws Exception {
        when(permissionChecker.hasPermission(any(User.class), Mockito.eq("UPDATE"))).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));

        mockMvc.perform(put("/products/updateProduct/1")
                        .requestAttr("user", getMockUser())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Laptop\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product updated!"));
    }

    @Test
    void updateProductWithoutPermission() throws Exception {
        when(permissionChecker.hasPermission(any(User.class), Mockito.eq("UPDATE"))).thenReturn(false);
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));

        mockMvc.perform(put("/products/updateProduct/1")
                        .requestAttr("user", getMockUser())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Laptop\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteProductWithPermission() throws Exception {
        when(permissionChecker.hasPermission(any(User.class), Mockito.eq("DELETE"))).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));

        mockMvc.perform(delete("/products/deleteProduct/1")
                        .requestAttr("user", getMockUser()))
                .andExpect(status().isOk())
                .andExpect(content().string("Product with id 1 deleted!"));
    }

    @Test
    void deleteProductWithPermissionProductNotFound() throws Exception {
        when(permissionChecker.hasPermission(any(User.class), Mockito.eq("DELETE"))).thenReturn(true);

        mockMvc.perform(delete("/products/deleteProduct/1")
                        .requestAttr("user", getMockUser()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Product with id 1 not found"));
    }
}
