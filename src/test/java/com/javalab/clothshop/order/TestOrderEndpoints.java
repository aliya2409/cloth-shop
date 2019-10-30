package com.javalab.clothshop.order;

import com.javalab.clothshop.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class TestOrderEndpoints {
    private static final String ORDER_URL = "http://localhost:8080/api/orders";
    private static final int TEST_ORDER_ID = 6;
    private static final int TEST_ORDER_ITEM_ID = 5;
    private static final String TEST_ORDER_ITEMS_URL = "http://localhost:8080/api/orders/" + TEST_ORDER_ID + "/items";
    private static final String TEST_ORDER_ITEM_URL = TEST_ORDER_ITEMS_URL + "/" + TEST_ORDER_ITEM_ID;

    @Value("${test.jsessionid}")
    private String jsessionid;

    @Test
    void when_getAll_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                ORDER_URL,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getItem_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                TEST_ORDER_ITEM_URL,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_addItem_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                TEST_ORDER_ITEMS_URL,
                HttpMethod.POST,
                new HttpEntity<>(createNewOrderItem(), getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    private OrderItem createNewOrderItem() {
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
        return item;
    }

    @Test
    void when_deleteItem_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                TEST_ORDER_ITEM_URL,
                HttpMethod.DELETE,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getById_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                ORDER_URL + "/" + TEST_ORDER_ID,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                Order.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_getOrderItems_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                TEST_ORDER_ITEMS_URL,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_purchaseOrder_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                ORDER_URL + "/" + TEST_ORDER_ID + "/purchase",
                HttpMethod.POST,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_cancelOrder_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                ORDER_URL + "/" + TEST_ORDER_ID + "/cancel",
                HttpMethod.POST,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_delete_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                ORDER_URL + "/" + TEST_ORDER_ID,
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
