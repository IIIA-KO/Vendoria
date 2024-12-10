package com.vendoria.apiservice;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.product.dto.ProductDto;
import com.vendoria.product.entity.Product;
import com.vendoria.product.errors.ProductErrors;
import com.vendoria.product.mapper.ProductDtoMapper;
import com.vendoria.product.persistence.ProductRepository;
import com.vendoria.product.requests.CreateProductRequest;
import com.vendoria.product.requests.UpdateProductRequest;
import com.vendoria.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDtoMapper productDtoMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setStockQuantity(10); // Додайте цю лінію, щоб ініціалізувати stockQuantity
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(productDtoMapper.mapProductListToProductDtoList(any())).thenReturn(Collections.singletonList(new ProductDto()));

        // Act
        ResultWithValue<List<ProductDto>> result = productService.getAllProducts();

        // Assert
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.value()).isNotEmpty();
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productDtoMapper.mapProductToProductDto(product)).thenReturn(new ProductDto());

        // Act
        ResultWithValue<Product> result = productService.findById(productId);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.value()).isNotNull();
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        ResultWithValue<Product> result = productService.findById(productId);

        // Assert
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getError()).isEqualTo(ProductErrors.NOT_FOUND);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testFindByName() {
        // Arrange
        String productName = "Test Product";
        Product product = new Product();
        product.setName(productName);
        product.setStockQuantity(10);
        when(productRepository.findByNameContainingIgnoreCase(productName)).thenReturn(Collections.singletonList(product));
        when(productDtoMapper.mapProductListToProductDtoList(any())).thenReturn(Collections.singletonList(new ProductDto()));

        // Act
        ResultWithValue<List<ProductDto>> result = productService.findByName(productName);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.value()).isNotEmpty();
        verify(productRepository, times(1)).findByNameContainingIgnoreCase(productName);
    }

    @Test
    void testCreateProduct() {
        // Arrange
        CreateProductRequest request = new CreateProductRequest("New Product", "Description", 100.0f, 10);
        Product product = new Product();
        product.setId(1L);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        when(productRepository.save(any())).thenReturn(product);
        when(productDtoMapper.mapProductToProductDto(product)).thenReturn(new ProductDto());

        // Act
        Result result = productService.createProduct(request);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Long productId = 1L;
        UpdateProductRequest request = new UpdateProductRequest("Updated Product", "Updated Description", 150.0f, 5);
        Product product = new Product();
        product.setId(productId);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);
        when(productDtoMapper.mapProductToProductDto(product)).thenReturn(new ProductDto());

        // Act
        Result result = productService.updateProduct(productId, request);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));

        // Act
        Result result = productService.deleteProduct(productId);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        verify(productRepository, times(1)).delete(productId);
    }
}
