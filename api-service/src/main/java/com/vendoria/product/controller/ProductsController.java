package com.vendoria.product.controller;

import com.vendoria.common.controllers.BaseApiController;
import com.vendoria.product.entity.Product;
import com.vendoria.product.requests.CreateProductRequest;
import com.vendoria.product.requests.UpdateProductRequest;
import com.vendoria.product.service.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductsController extends BaseApiController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return handleResultWithValue(productService.getAllProducts());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        return handleResultWithValue(productService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody CreateProductRequest request) {
        return handleResult(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        return handleResult(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return handleResult(productService.deleteProduct(id));
    }
}