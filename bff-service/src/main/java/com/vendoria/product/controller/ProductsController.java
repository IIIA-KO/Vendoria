package com.vendoria.product.controller;

import com.vendoria.common.handlers.ErrorHandler;
import com.vendoria.product.requests.CreateProductRequest;
import com.vendoria.product.requests.UpdateProductRequest;
import com.vendoria.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @GetMapping("/products")
    public ModelAndView showProducts(@RequestParam(required = false) String search) {
        if (search == null || search.isEmpty()) {
            var result = productService.getAllProducts();

            return ErrorHandler.handleResultWithValue(result, "/products/list", "/products/list", "products");
        } else {
            var searchResult = productService.findByName(search);
            return ErrorHandler.handleResultWithValue(searchResult, "/products/list", "/products/list", "products");
        }
    }

    @GetMapping("/admin/products")
    public ModelAndView showAdminProducts(Model model) {
        var result = productService.getAllProducts();

        return ErrorHandler.handleResultWithValue(result, "admin/products", "error", "products");
    }

    @PostMapping("/admin/products")
    public ModelAndView createProduct(@Valid CreateProductRequest request) {
        var result = productService.createProduct(request);

        return ErrorHandler.handleResult(result, "/admin/products", "/admin/products");
    }

    @PostMapping("/admin/products/{id}")
    public ModelAndView updateProduct(
            @PathVariable Long id,
            @Valid UpdateProductRequest request
    ) {
        var result = productService.updateProduct(id, request);

        return ErrorHandler.handleResult(result, "/admin/products", "/admin/products");
    }

    @PostMapping("/admin/products/{id}/delete")
    public ModelAndView deleteProduct(@PathVariable Long id) {
        var result = productService.deleteProduct(id);

        return ErrorHandler.handleResult(result, "/admin/products", "/admin/products");
    }
}