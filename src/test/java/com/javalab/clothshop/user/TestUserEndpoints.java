package com.javalab.clothshop.user;

import com.javalab.clothshop.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class TestUserEndpoints {
    private static final String USER_URL = "http://localhost:8080/api/users";
    private static final int TEST_USER_ID = 4;

    @Value("${test.jsessionid}")
    private String jsessionid;

    @Test
    void when_getAll_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                USER_URL,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getById_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                USER_URL + "/" + TEST_USER_ID,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                User.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getOrders_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                USER_URL + "/" + TEST_USER_ID + "/orders",
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_createOrder_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                USER_URL + "/" + TEST_USER_ID + "/orders",
                HttpMethod.POST,
                new HttpEntity<>(createNewTestOrder(), getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_create_then_Ok() {
        User newUser = User.builder()
                .username("test")
                .firstName("Test")
                .lastName("Create")
                .email("unique@gmail.com")
                .password("$2y$12$2FCSV902ZDQpk/rp08kUXu5EgCQlXdrHrkNwEgChjUOJ3fyuZzYnq")
                .phone("+74951234568")
                .build();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity<>(newUser, getHeadersWithAuthentication());
        ResponseEntity response = restTemplate.exchange(
                USER_URL,
                HttpMethod.POST,
                request,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_update_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        User retrieved = restTemplate.exchange(
                USER_URL + "/" + TEST_USER_ID,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                User.class).getBody();
        retrieved.setUsername("Updated");
        HttpEntity request = new HttpEntity<>(retrieved, getHeadersWithAuthentication());
        ResponseEntity response = restTemplate.exchange(
                USER_URL + "/" + TEST_USER_ID,
                HttpMethod.PUT,
                request,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_delete_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                USER_URL + "/" + TEST_USER_ID,
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

    private Order createNewTestOrder() {
        User user = User.builder()
                .username("bastion")
                .firstName("Vincent")
                .lastName("Guerrero")
                .email("aliya2409@gmail.com")
                .password("$2y$12$2FCSV902ZDQpk/rp08kUXu5EgCQlXdrHrkNwEgChjUOJ3fyuZzYnq") //"password"
                .phone("+74951234567")
                .build();
        user.setId(4L);
        Category category = new Category();
        category.setName("Electronics");
        category.setId(1L);
        Vendor vendor = new Vendor();
        vendor.setName("Guandzhou");
        vendor.setId(2L);
        Product product = Product.builder()
                .name("Mi Band 3").price(5000).quantity(2).category(category).vendor(vendor).build();
        product.setId(3L);
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(1);
        return Order.builder()
                .complete(false)
                .createdAt(LocalDateTime.now())
                .shipDate(LocalDateTime.now())
                .status(OrderStatus.PLACED)
                .user(user)
                .item(item).build();
    }
}
