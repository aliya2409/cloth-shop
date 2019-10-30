package com.javalab.clothshop.order;

import com.javalab.clothshop.model.*;
import com.javalab.clothshop.service.order.OrderRemovalService;
import com.javalab.clothshop.service.order.OrderSavingService;
import com.javalab.clothshop.service.product.ProductRetrievalService;
import com.javalab.clothshop.service.user.UserRetrievalService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOrderEndpoints {
    private static final String ORDER_URL = "http://localhost:8080/api/orders";
    private static final long TEST_ORDER_ID = 6;
    private static final long TEST_ORDER_ITEM_ID = 5;
    private static final String TEST_ORDER_ITEMS_URL = ORDER_URL + "/" + TEST_ORDER_ID + "/items";
    private static final String TEST_ORDER_ITEM_URL = TEST_ORDER_ITEMS_URL + "/" + TEST_ORDER_ITEM_ID;
    private static final long TEST_PRODUCT_ID = 3L;
    private static final long TEST_USER_ID = 4L;
    public static final String DELETE_SUCCESSFUL_TEST_NAME = "when_delete_then_Ok()";

    @Value("${test.jsessionid}")
    private String jsessionid;
    @Autowired
    private ProductRetrievalService productRetrievalService;
    @Autowired
    private UserRetrievalService userRetrievalService;
    @Autowired
    private OrderSavingService orderSavingService;
    @Autowired
    private OrderRemovalService orderRemovalService;

    private Order order;

    @BeforeEach
    void setup() {
        order = createTestOrder();
        order = orderSavingService.save(order);
    }

    @AfterEach
    void cleanup(TestInfo testInfo) {
        if (!testInfo.getDisplayName().equals(DELETE_SUCCESSFUL_TEST_NAME)) {
            orderRemovalService.removeById(order.getId());
        }
    }

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
        Product product = productRetrievalService.retrieveById(TEST_PRODUCT_ID);
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
                ORDER_URL + "/" + order.getId() + "/purchase",
                HttpMethod.POST,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_cancelOrder_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                ORDER_URL + "/" + order.getId() + "/cancel",
                HttpMethod.POST,
                new HttpEntity<>(null, getHeadersWithAuthentication()),
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void when_delete_then_Ok() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                ORDER_URL + "/" + order.getId(),
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

    private Order createTestOrder() {
        User user = userRetrievalService.retrieveBy(TEST_USER_ID);
        Product product = productRetrievalService.retrieveById(TEST_PRODUCT_ID);
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
