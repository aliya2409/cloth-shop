package com.javalab.clothshop.product;

import com.javalab.clothshop.model.Category;
import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.model.Vendor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class TestProductEndpoints {
    private static final String PRODUCT_URL = "http://localhost:8080/api/products";
    private static final int TEST_PRODUCT_ID = 3;

    @Value("${test.jsessionid}")
    private String jsessionid;

    @Test
    void when_getAll_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                PRODUCT_URL,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getById_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                PRODUCT_URL + "/" + TEST_PRODUCT_ID,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                Product.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_create_then_Ok() {
        Product newProduct = new Product();
        Category category = new Category();
        category.setName("Electronics");
        category.setId(1L);
        newProduct.setCategory(category);
        Vendor vendor = new Vendor();
        vendor.setName("Guandzhou");
        vendor.setId(2L);
        newProduct.setVendor(vendor);
        newProduct.setName("Kinder");
        newProduct.setQuantity(3);
        newProduct.setPrice(100);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity<>(newProduct, getHeadersWithAuthentication());
        ResponseEntity response = restTemplate.exchange(
                PRODUCT_URL,
                HttpMethod.POST,
                request,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_update_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        Product retrieved = restTemplate.exchange(
                PRODUCT_URL + "/" + TEST_PRODUCT_ID,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                Product.class).getBody();
        retrieved.setName("Milky way");
        HttpEntity request = new HttpEntity<>(retrieved, getHeadersWithAuthentication());
        ResponseEntity response = restTemplate.exchange(
                PRODUCT_URL + "/" + TEST_PRODUCT_ID,
                HttpMethod.PUT,
                request,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_delete_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                PRODUCT_URL + "/" + TEST_PRODUCT_ID,
                HttpMethod.DELETE,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    private HttpHeaders getHeadersWithAuthentication() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "JSESSIONID=" + jsessionid);
        return requestHeaders;
    }
}
