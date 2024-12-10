package com.vendoria.bffservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.vendoria.bff.feign.client.IVendoriaApiClient;
import com.vendoria.bff.feign.config.FeignConfig;
import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.product.dto.ProductDto;
import com.vendoria.product.requests.CreateProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
@WireMockTest
@SpringBootTest(classes = VendoriaApiClientTest.class)
public class VendoriaApiClientTest {
    @Autowired
    private IVendoriaApiClient vendoriaApiClient;

    @BeforeEach
    void setUp() {
        WireMock.reset();
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Test Product");

        stubFor(get(urlEqualTo("/api/products"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1, \"name\": \"Test Product\"}]")));

        // Act
        List<ProductDto> products = vendoriaApiClient.getAllProducts();

        // Assert
        assertThat(products).isNotEmpty();
        assertThat(products.get(0).getName()).isEqualTo("Test Product");
    }

    @Test
    void testGetProductsByName() {
        // Arrange
        String productName = "Test Product";
        stubFor(get(urlEqualTo("/api/products/" + productName))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1, \"name\": \"Test Product\"}]")));

        // Act
        ResultWithValue<List<ProductDto>> result = vendoriaApiClient.getProductsByName(productName);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.value()).isNotEmpty();
        assertThat(result.value().get(0).getName()).isEqualTo("Test Product");
    }

    @Test
    void testCreateProduct() {
        // Arrange
        CreateProductRequest request = new CreateProductRequest("New Product", "Description", 100.0f, 10);
        stubFor(post(urlEqualTo("/api/products"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"success\": true}")));

        // Act
        Result result = vendoriaApiClient.createProduct(request);

        // Assert
        assertThat(result.isSuccess()).isTrue();
    }

    @Configuration
    @EnableFeignClients(clients = IVendoriaApiClient.class)
    static class TestConfig {
        @Bean
        public FeignConfig feignConfig() {
            return new FeignConfig();
        }
    }
}
