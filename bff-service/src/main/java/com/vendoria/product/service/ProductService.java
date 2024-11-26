package com.vendoria.product.service;

import com.vendoria.bff.feign.client.IVendoriaApiClient;
import com.vendoria.bff.feign.decoder.FeignResultException;
import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.errors.Error;
import com.vendoria.product.dto.ProductDto;
import com.vendoria.product.requests.CreateProductRequest;
import com.vendoria.product.requests.UpdateProductRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final IVendoriaApiClient vendoriaApiClient;

    public ResultWithValue<List<ProductDto>> getAllProducts() {
        try {
            var products = vendoriaApiClient.getAllProducts();
            return ResultWithValue.successWithValue(products);
        } catch (Exception e) {
            log.error("API error getting products: {}", e.getMessage());
            return ResultWithValue.failureWithResult(Error.UNEXPECTED);
        }
    }

    public ResultWithValue<List<ProductDto>> findByName(String name) {
        try {
            var result = vendoriaApiClient.getProductsByName(name);
            if (result.isSuccess()) {
                return result;
            }
            return ResultWithValue.successWithValue(new ArrayList<>());
        } catch (FeignResultException e) {
            log.error("API error searching products: {}", e.getMessage());
            return ResultWithValue.successWithValue(new ArrayList<>());
        }
    }

    public Result createProduct(CreateProductRequest request) {
        try {
            return vendoriaApiClient.createProduct(request);
        } catch (FeignResultException e) {
            log.error("API error creating product: {}", e.getMessage());
            return Result.failure(e.getError());
        }
    }

    public Result updateProduct(Long id, UpdateProductRequest request) {
        try {
            return vendoriaApiClient.updateProduct(id, request);
        } catch (FeignResultException e) {
            log.error("API error updating product: {}", e.getMessage());
            return Result.failure(e.getError());
        }
    }

    public Result deleteProduct(Long id) {
        try {
            return vendoriaApiClient.deleteProduct(id);
        } catch (FeignResultException e) {
            log.error("API error deleting product: {}", e.getMessage());
            return Result.failure(e.getError());
        }
    }
}
