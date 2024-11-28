package com.vendoria.product.service;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.errors.Error;
import com.vendoria.product.dto.ProductDto;
import com.vendoria.product.entity.Product;
import com.vendoria.product.errors.ProductErrors;
import com.vendoria.product.mapper.ProductDtoMapper;
import com.vendoria.product.persistence.ProductRepository;
import com.vendoria.product.requests.CreateProductRequest;
import com.vendoria.product.requests.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    public ResultWithValue<Product> findById(Long id) {
        try {
            var product = productRepository.findById(id);
            return product.map(ResultWithValue::successWithValue).orElseGet(() -> ResultWithValue.failureWithResult(ProductErrors.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error finding product by id", e);
            return ResultWithValue.failureWithResult(Error.UNEXPECTED);
        }
    }

    public ResultWithValue<List<ProductDto>> getAllProducts() {
        try {
            var products = productRepository.findAll();
            var productDtos = productDtoMapper.mapProductListToProductDtoList(products);

            return ResultWithValue.successWithValue(productDtos);
        } catch (Exception e) {
            log.error("Error getting all products", e);
            return ResultWithValue.failureWithResult(Error.UNEXPECTED);
        }
    }

    public ResultWithValue<List<ProductDto>> findByName(String name) {
        try {
            var products = productRepository.findByNameContainingIgnoreCase(name);
            var productDtos = productDtoMapper.mapProductListToProductDtoList(products);

            return ResultWithValue.successWithValue(productDtos);
        } catch (Exception e) {
            log.error("Error getting all products", e);
            return ResultWithValue.failureWithResult(Error.UNEXPECTED);
        }
    }

    public Result createProduct(CreateProductRequest request) {
        try {
            Product product = Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .stockQuantity(request.getStockQuantity())
                    .build();

            productRepository.save(product);
            return Result.success();
        } catch (Exception e) {
            log.error("Error creating product", e);
            return Result.failure(Error.UNEXPECTED);
        }
    }

    public Result updateProduct(Long id, UpdateProductRequest request) {
        try {
            var existingProduct = productRepository.findById(id);
            if (existingProduct.isEmpty()) {
                return Result.failure(ProductErrors.NOT_FOUND);
            }

            Product product = Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .stockQuantity(request.getStockQuantity())
                    .build();

            productRepository.update(id, product);
            return Result.success();
        } catch (Exception e) {
            log.error("Error updating product", e);
            return Result.failure(Error.UNEXPECTED);
        }
    }

    public Result decreaseStock(Long productId, int quantity) {
        try {
            var productOptional = productRepository.findById(productId);
            if (productOptional.isEmpty()) {
                return Result.failure(ProductErrors.NOT_FOUND);
            }

            Product product = productOptional.get();
            if (product.getStockQuantity() < quantity) {
                return Result.failure(ProductErrors.INSUFFICIENT_STOCK);
            }

            product.setStockQuantity(product.getStockQuantity() - quantity);

            return Result.success();
        } catch (Exception e) {
            log.error("Error decreasing stock for product id {}", productId, e);
            return Result.failure(Error.UNEXPECTED);
        }
    }

    public Result deleteProduct(Long id) {
        try {
            productRepository.delete(id);
            return Result.success();
        } catch (Exception e) {
            log.error("Error deleting product", e);
            return Result.failure(Error.UNEXPECTED);
        }
    }
}
